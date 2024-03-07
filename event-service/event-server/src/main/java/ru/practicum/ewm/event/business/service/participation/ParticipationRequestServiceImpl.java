package ru.practicum.ewm.event.business.service.participation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.event.business.exception.BusinessLogicException;
import ru.practicum.ewm.event.business.exception.NotFoundException;
import ru.practicum.ewm.event.persistence.entity.EventEntity;
import ru.practicum.ewm.event.persistence.entity.ParticipationRequestEntity;
import ru.practicum.ewm.event.persistence.entity.UserEntity;
import ru.practicum.ewm.event.persistence.repository.EventRepository;
import ru.practicum.ewm.event.persistence.repository.ParticipationRequestRepository;
import ru.practicum.ewm.event.persistence.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static ru.practicum.ewm.event.persistence.entity.ParticipationRequestEntity.ParticipationRequestState;
import static ru.practicum.ewm.event.persistence.entity.ParticipationRequestEntity.ParticipationRequestState.*;
import static ru.practicum.ewm.event.util.MessageFormatter.format;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ParticipationRequestServiceImpl implements ParticipationRequestService {

    private final ParticipationRequestRepository participationRequestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ParticipationRequestEntity> findParticipationRequests(long userId, long eventId) {
        log.info("Получение всех запросов на участие в событии с id {} инициатора события с id {}.", userId, eventId);

        return participationRequestRepository.findByEventIdAndEventInitiatorId(eventId, userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParticipationRequestEntity> findParticipationRequests(long userId) {
        log.info("Получение заявок пользователя с id {} на участие в чужих событиях.", userId);

        return participationRequestRepository.findByRequesterId(userId);
    }

    @Override
    public ParticipationRequestEntity createParticipationRequest(long userId, long eventId) {
        log.info("Создание заявки на участие пользователя с id {} в событии с id {}.", userId, eventId);

        UserEntity requester = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(
                                format("Пользователь с id {} не найден.", userId),
                                "Отсутствуют сведения в базе данных."
                        )
                );

        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(
                                format("Событие с id {} не найдено.", eventId),
                                "Отсутствуют сведения в базе данных."
                        )
                );

        ParticipationRequestEntity participationRequest = new ParticipationRequestEntity();
        participationRequest.setEvent(event);
        participationRequest.setRequester(requester);
        participationRequest.setState(PENDING);

        if (participationRequestRepository.existsByEventIdAndRequesterId(eventId, userId)) {
            throw new BusinessLogicException("Нельзя добавить повторный запрос.", "Предоставлены неверные данные.");
        }

        if (event.getInitiator().equals(requester)) {
            throw new BusinessLogicException("Инициатор события не может добавить запрос на участие в своём событии.",
                    "Является владельцем.");
        }

        if (event.getState() != EventEntity.EventState.PUBLISHED) {
            throw new BusinessLogicException("Нельзя участвовать в неопубликованном событии.",
                    "Не соответствие статуса события.");
        }

        if (event.getParticipantLimit() != 0 && event.getConfirmedRequests() == (int) event.getParticipantLimit()) {
            throw new BusinessLogicException("Невозможно создать участие.", "Достигнут лимит запросов на участие.");
        }

        validateParticipationLimit(event);

        if (!event.isRequestModeration() || event.getParticipantLimit() == 0) {
            confirmParticipationRequest(participationRequest);
        }

        return participationRequestRepository.save(participationRequest);
    }

    @Override
    public ParticipationRequestEntity cancelParticipationRequest(long userId, long requestId) {
        log.info("Отмена заявки на участие с id {} для пользователя с id {}.", requestId, userId);

        ParticipationRequestEntity request = participationRequestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException(
                                format("Заявка на участие с id {} не найдена.", requestId),
                                "Отсутствуют сведения в базе данных."
                        )
                );

        if (!request.getRequester().getId().equals(userId)) {
            throw new BusinessLogicException("Невозможно отменить заявку.", "Запрещено отменять заявку не владельцу.");
        }

        cancelParticipationRequest(request);
        return request;
    }

    @Override
    public List<List<ParticipationRequestEntity>>
    updateParticipationRequestsStatuses(long userId, long eventId,
                                        List<Long> requestIds,
                                        ParticipationRequestState newStatus) {
        log.info("Обновление заявок на участие для события с id {} пользователем с id {}. " +
                "Идентификаторы запросов {}. Новый статус {}.", eventId, userId, requestIds, newStatus);

        List<ParticipationRequestEntity> requests = participationRequestRepository.findAllById(requestIds);

        List<List<ParticipationRequestEntity>> result = new ArrayList<>();
        result.add(new ArrayList<>());
        result.add(new ArrayList<>());

        requests.forEach(currentRequest -> {
            if (!currentRequest.getState().equals(ParticipationRequestEntity.ParticipationRequestState.PENDING)) {
                throw new BusinessLogicException("Невозможно изменить статус.",
                        "Статус можно изменить только у заявок, находящихся в состоянии ожидания.");
            }

            EventEntity currentEvent = currentRequest.getEvent();
            int limit = currentEvent.getParticipantLimit();

            if (newStatus == REJECTED) {
                currentRequest.setState(ParticipationRequestEntity.ParticipationRequestState.REJECTED);
                result.get(1).add(currentRequest);
            } else if (newStatus == CONFIRMED) {
                if (limit == 0 || !currentEvent.isRequestModeration() || currentEvent.getConfirmedRequests() < limit) {
                    confirmParticipationRequest(currentRequest);
                    result.get(0).add(currentRequest);
                } else {
                    currentRequest.setState(ParticipationRequestEntity.ParticipationRequestState.REJECTED);
                    result.get(1).add(currentRequest);
                }
            }
        });

        return result;
    }

    private void validateParticipationLimit(EventEntity event) {
        int limit = event.getParticipantLimit();

        if (limit > 0 && event.getConfirmedRequests() == limit) {
            throw new BusinessLogicException("Превышен лимит участников.");
        }
    }

    private void confirmParticipationRequest(ParticipationRequestEntity request) {
        EventEntity currentEvent = request.getEvent();
        ParticipationRequestState currentState = request.getState();

        if (!currentState.equals(PENDING)) {
            throw new BusinessLogicException("Невозможно утвердить заявку.",
                    "Утвердить можно только заявки, находящиеся в состоянии ожидания.");
        }

        currentEvent.setConfirmedRequests(currentEvent.getConfirmedRequests() + 1);
        request.setState(CONFIRMED);
    }

    private void cancelParticipationRequest(ParticipationRequestEntity request) {
        EventEntity currentEvent = request.getEvent();
        ParticipationRequestState currentState = request.getState();

        if (currentState == CONFIRMED) {
            currentEvent.setConfirmedRequests(currentEvent.getConfirmedRequests() - 1);
        }

        request.setState(CANCELED);
    }
}
