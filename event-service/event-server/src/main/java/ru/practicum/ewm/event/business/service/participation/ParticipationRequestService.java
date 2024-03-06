package ru.practicum.ewm.event.business.service.participation;

import ru.practicum.ewm.event.business.exception.BusinessLogicException;
import ru.practicum.ewm.event.business.exception.NotFoundException;
import ru.practicum.ewm.event.persistence.entity.ParticipationRequestEntity;

import java.util.List;

import static ru.practicum.ewm.event.persistence.entity.ParticipationRequestEntity.ParticipationRequestState;

/**
 * Сервис запросов на участие в событиях.
 */
public interface ParticipationRequestService {

    /**
     * Получение информации о запросах на участие в событии текущего пользователя других пользователей.
     *
     * @param userId  id пользователя
     * @param eventId id события
     * @return список заявок на участие.
     */
    List<ParticipationRequestEntity> findParticipationRequests(long userId, long eventId);

    /**
     * Получение информации о заявках текущего пользователя на участие в чужих событиях.
     *
     * @param userId id пользователя
     * @return список заявок на участие.
     */
    List<ParticipationRequestEntity> findParticipationRequests(long userId);

    /**
     * Создание заявки на участие.
     *
     * @param userId  id пользователя
     * @param eventId id события
     * @return созданная заявка на участие.
     * @throws NotFoundException      если пользователь или событие не найдено.
     * @throws BusinessLogicException если ошибка в логике (повторный запрос, неверный статус и т.п.).
     */
    ParticipationRequestEntity createParticipationRequest(long userId, long eventId);

    /**
     * Отмена запроса на участие в событии.
     *
     * @param userId    id пользователя
     * @param requestId id запроса
     * @return отмененный запрос.
     * @throws BusinessLogicException если меняет заявку не владелец.
     * @throws NotFoundException      если заявка не найдена.
     */
    ParticipationRequestEntity cancelParticipationRequest(long userId, long requestId);

    /**
     * Групповое обновление статусов заявок на участие.
     *
     * @param userId     id пользователя
     * @param eventId    id события
     * @param requestIds список id запросов на участие
     * @param newStatus  целевой статус для изменения
     * @return список списков {@link ParticipationRequestEntity},
     * [0] - подтвержденные запросы, [1] - отклоненные запросы.
     */
    List<List<ParticipationRequestEntity>> updateParticipationRequestsStatuses(long userId, long eventId,
                                                                               List<Long> requestIds,
                                                                               ParticipationRequestState newStatus);
}
