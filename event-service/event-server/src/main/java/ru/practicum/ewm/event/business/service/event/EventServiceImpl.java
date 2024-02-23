package ru.practicum.ewm.event.business.service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.event.business.copier.EventCopier;
import ru.practicum.ewm.event.business.dto.EventSearchParameters;
import ru.practicum.ewm.event.business.exception.NotFoundException;
import ru.practicum.ewm.event.business.exception.ValidationException;
import ru.practicum.ewm.event.business.model.EventStateAction;
import ru.practicum.ewm.event.business.model.SubjectInfo;
import ru.practicum.ewm.event.business.model.SubjectRole;
import ru.practicum.ewm.event.persistence.entity.EventEntity;
import ru.practicum.ewm.event.persistence.entity.ParticipationRequestEntity;
import ru.practicum.ewm.event.persistence.entity.UserEntity;
import ru.practicum.ewm.event.persistence.enums.EventSortBy;
import ru.practicum.ewm.event.persistence.repository.EventRepository;
import ru.practicum.ewm.event.persistence.repository.UserRepository;
import ru.practicum.ewm.event.util.PageableUtil;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.ewm.event.util.MessageFormatter.format;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventCopier eventCopier;

    @Override
    @Transactional(readOnly = true)
    public List<EventEntity> findEvents(SubjectInfo subjectInfo, EventSearchParameters searchParameters,
                                        EventSortBy sortBy, long from, int size) {

        return eventRepository.findEvents(searchParameters, sortBy, PageableUtil.of(from, size));
    }

    @Override
    @Transactional(readOnly = true)
    public EventEntity findEvent(SubjectInfo subjectInfo, long eventId) {
        if (subjectInfo.isUser()) {
            long userId = subjectInfo.getUserId();
            return eventRepository.findEventEntityByInitiatorIdAndId(subjectInfo.getUserId(), eventId)
                    .orElseThrow(() -> new NotFoundException(
                            format("Событие с id {} для пользователя с id {} не найдено", eventId, userId),
                            "Отсутствуют сведения в базе данных"));
        } else {
            return eventRepository.findEventEntityById(eventId)
                    .orElseThrow(() -> new NotFoundException(
                            format("Событие с id {} не найдено", eventId),
                            "Отсутствуют сведения в базе данных"));
        }
    }

    @Override
    public EventEntity createEvent(SubjectInfo subjectInfo, EventEntity event) {
        long userId = subjectInfo.getUserId();

        UserEntity initiator = userRepository.findById(userId)
                .orElseThrow(() -> new ValidationException(
                        format("Пользователь с id {} не найден", userId),
                        "Отсутствуют сведения в базе данных"));

        event.setInitiator(initiator);
        return eventRepository.save(event);
    }

    @Override
    public EventEntity updateEvent(SubjectInfo subjectInfo, long eventId, EventEntity event,
                                   EventStateAction stateAction) {

        long userId = subjectInfo.getUserId();
        SubjectRole role = subjectInfo.getRole();

        EventEntity savedEvent = eventRepository.findEventEntityById(eventId)
                .orElseThrow(() -> new NotFoundException(
                        format("Событие с id {} не найдено", eventId),
                        "Отсутствуют сведения в базе данных"));

        if (role == SubjectRole.USER && !savedEvent.getInitiator().getId().equals(userId)) {
            throw new ValidationException(
                    format("Пользователю с id {} не разрешено внести изменения в событие с id {}", userId, event
                    ), "Не является владельцем.");
        }

        EventEntity.EventState eventCurrentState = savedEvent.getState();
        EventEntity.EventState nextState = EventStatechartHelper.next(role, stateAction, eventCurrentState)
                .orElseThrow(() -> new ValidationException(
                        format("Пользователю с id {} не разрешено изменить состояние события с id {}", userId, event
                        ), "Действие не сопоставимо с текущей ролью и состоянием."));


        eventCopier.update(savedEvent, event);
        savedEvent.setState(nextState);

        if (nextState == EventEntity.EventState.PUBLISHED) {
            savedEvent.setPublished(LocalDateTime.now());
        }

        return savedEvent;
    }

    @Override
    public void addOneViewToEvent(long eventId) {
        eventRepository.addOneViewToEvent(eventId);
    }
}
