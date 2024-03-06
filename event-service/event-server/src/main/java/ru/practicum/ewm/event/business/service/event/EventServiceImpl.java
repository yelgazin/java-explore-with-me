package ru.practicum.ewm.event.business.service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.event.business.copier.EventCopier;
import ru.practicum.ewm.event.business.dto.EventSearchParameters;
import ru.practicum.ewm.event.business.exception.BusinessLogicException;
import ru.practicum.ewm.event.business.exception.NotFoundException;
import ru.practicum.ewm.event.business.exception.ValidationException;
import ru.practicum.ewm.event.business.model.EventStateAction;
import ru.practicum.ewm.event.business.model.SubjectInfo;
import ru.practicum.ewm.event.persistence.entity.CategoryEntity;
import ru.practicum.ewm.event.persistence.entity.EventEntity;
import ru.practicum.ewm.event.persistence.entity.UserEntity;
import ru.practicum.ewm.event.persistence.enums.EventSortBy;
import ru.practicum.ewm.event.persistence.repository.CategoryRepository;
import ru.practicum.ewm.event.persistence.repository.EventRepository;
import ru.practicum.ewm.event.persistence.repository.UserRepository;
import ru.practicum.ewm.event.util.PageableUtil;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.ewm.event.persistence.entity.EventEntity.EventState;
import static ru.practicum.ewm.event.util.MessageFormatter.format;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    public static final int EVENT_START_HOURS_LAG = 2;

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EventCopier eventCopier;

    @Override
    @Transactional(readOnly = true)
    public List<EventEntity> findEvents(EventSearchParameters searchParameters,
                                        EventSortBy sortBy, long from, int size) {
        log.info("Поиск событий по параметрам {}. Сортировка {}. Начиная с {}, количество объектов {}.",
                searchParameters, sortBy, from, size);

        LocalDateTime rangeStart = searchParameters.getRangeStart();
        LocalDateTime rangeEnd = searchParameters.getRangeEnd();
        if (rangeEnd != null && rangeEnd.isBefore(rangeStart)) {
            throw new ValidationException("Дата окончания периода не может быть раньше даты начала.",
                    "Предоставлены неверные данные.");
        }

        return eventRepository.findEvents(searchParameters, sortBy, PageableUtil.of(from, size));
    }

    @Override
    @Transactional(readOnly = true)
    public EventEntity findEventByUserId(long userId, long eventId) {
        log.info("Поиск события по id пользователя {} и id события {}.", userId, eventId);

        return eventRepository.findEventEntityByInitiatorIdAndId(userId, eventId)
                .orElseThrow(() -> new NotFoundException(
                                format("Событие с id {} для пользователя с id {} не найдено.", eventId, userId),
                                "Отсутствуют сведения в базе данных."
                        )
                );
    }

    @Override
    @Transactional(readOnly = true)
    public EventEntity findEvent(long eventId) {
        log.info("Поиск события по id события {}.", eventId);

        return eventRepository.findEventEntityByIdAndState(eventId, EventEntity.EventState.PUBLISHED)
                .orElseThrow(() -> new NotFoundException(
                                format("Событие с id {} не найдено.", eventId),
                                "Отсутствуют сведения в базе данных."
                        )
                );
    }

    @Override
    public EventEntity createEvent(long userId, EventEntity event) {
        log.info("Создание события пользователем с id {}. {}.", userId, event);

        UserEntity initiator = userRepository.findById(userId)
                .orElseThrow(() -> new ValidationException(
                                format("Пользователь с id {} не найден.", userId),
                                "Отсутствуют сведения в базе данных."
                        )
                );

        event.setInitiator(initiator);

        if (event.getDate().isBefore(LocalDateTime.now().plusHours(EVENT_START_HOURS_LAG))) {
            throw new ValidationException(
                    format("Событие должно быть не ранее чем через {0} часа.", EVENT_START_HOURS_LAG),
                    "Предоставлены неверные данные."
            );
        }

        return eventRepository.save(event);
    }

    @Override
    public EventEntity updateEvent(SubjectInfo subjectInfo, long eventId, EventEntity sourceEvent,
                                   EventStateAction stateAction) {
        log.info("Обновление события пользователем с id {} и ролью {}. Действие {}. Параметры обновления {}.",
                subjectInfo.getUserId(), subjectInfo.getRole(), stateAction, sourceEvent);

        EventEntity targetEvent = eventRepository.findEventEntityById(eventId)
                .orElseThrow(() -> new NotFoundException(
                                format("Событие с id {} не найдено.", eventId),
                                "Отсутствуют сведения в базе данных."
                        )
                );

        validateEntityUpdate(subjectInfo, eventId, sourceEvent, targetEvent);

        ensureEventCategory(sourceEvent);
        eventCopier.update(targetEvent, sourceEvent);

        if (stateAction != null) {
            processAction(subjectInfo, eventId, stateAction, targetEvent);
        }

        return targetEvent;
    }

    @Override
    public void setViewsToEvent(long eventId, long views) {
        log.info("Обновление количества просмотров для события с id {}. Новое значение {}.", eventId, views);

        eventRepository.setViewsToEvent(eventId, views);
    }

    private static void processAction(SubjectInfo subjectInfo, long eventId, EventStateAction stateAction, EventEntity targetEvent) {
        Long userId = subjectInfo.getUserId();
        EventState eventCurrentState = targetEvent.getState();

        EventState nextState = EventStatechartHelper.next(subjectInfo.getRole(), stateAction, eventCurrentState)
                .orElseThrow(() -> new BusinessLogicException(
                                format("Пользователю с id {} не разрешено изменить состояние события с id {}.",
                                        userId, eventId
                                ), "Действие не сопоставимо с текущей ролью и состоянием."
                        )
                );

        targetEvent.setState(nextState);

        if (nextState == EventState.PUBLISHED) {
            targetEvent.setPublished(LocalDateTime.now());
        }
    }

    private static void validateEntityUpdate(SubjectInfo subjectInfo, long eventId,
                                             EventEntity sourceEvent, EventEntity targetEvent) {
        Long userId = subjectInfo.getUserId();
        EventState currentState = targetEvent.getState();

        if (subjectInfo.isUser() && !(currentState == EventState.PENDING ||
                currentState == EventState.CANCELED)) {
            throw new BusinessLogicException("Изменить можно только отмененные события или события в состоянии " +
                    "ожидания модерации.", "Предоставлены неверные данные.");
        }

        if (subjectInfo.isUser() && !targetEvent.getInitiator().getId().equals(userId)) {
            throw new ValidationException(
                    format("Пользователю с id {} не разрешено внести изменения в событие с id {}.",
                            userId, eventId
                    ), "Не является владельцем.");
        }

        if (sourceEvent.getDate() != null) {
            if (sourceEvent.getDate().isBefore(LocalDateTime.now().plusHours(EVENT_START_HOURS_LAG))) {
                throw new ValidationException(
                        format("Событие должно быть не ранее чем через {0} часа.", EVENT_START_HOURS_LAG),
                        "Предоставлены неверные данные."
                );
            }
        }
    }

    private void ensureEventCategory(EventEntity event) {
        CategoryEntity category = event.getCategory();
        if (category != null && category.getId() != null) {
            long categoryId = category.getId();
            category = categoryRepository.findById(category.getId())
                    .orElseThrow(() -> new NotFoundException(
                            format("Категория с id {} не найдена.", categoryId),
                            "Отсутствуют сведения в базе данных."
                    ));
            event.setCategory(category);
        } else {
            event.setCategory(null);
        }
    }
}
