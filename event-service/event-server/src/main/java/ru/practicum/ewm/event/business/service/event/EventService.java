package ru.practicum.ewm.event.business.service.event;

import ru.practicum.ewm.event.business.dto.EventSearchParameters;
import ru.practicum.ewm.event.business.dto.EventUpdateParameters;
import ru.practicum.ewm.event.business.exception.BusinessLogicException;
import ru.practicum.ewm.event.business.exception.NotFoundException;
import ru.practicum.ewm.event.business.exception.ValidationException;
import ru.practicum.ewm.event.business.model.EventStateAction;
import ru.practicum.ewm.event.business.model.SubjectInfo;
import ru.practicum.ewm.event.persistence.entity.EventEntity;
import ru.practicum.ewm.event.persistence.enums.EventSortBy;

import java.util.List;

/**
 * Сервис событий.
 */
public interface EventService {

    /**
     * Поиск событий по параметрам с последующей сортировкой.
     * Если указано сортировка, то дополнительно сортируется по id события.
     * Если не указан тип сортировки, то сортируется по id события.
     *
     * @param searchParameters параметры поиска
     * @param sortBy           тип сортировки
     * @param from             индекс первого объекта
     * @param size             максимальное кол-во возвращаемых объектов
     * @return список событий.
     * @throws ValidationException если переданы некорректные параметры.
     */
    List<EventEntity> findEvents(EventSearchParameters searchParameters,
                                 EventSortBy sortBy, long from, int size);

    /**
     * Поиск события, добавленного пользователем.
     *
     * @param userId  id пользователя
     * @param eventId id события
     * @return событие.
     * @throws NotFoundException если событие не найдено.
     */
    EventEntity findEventByUserId(long userId, long eventId);

    /**
     * Поиск события по идентификатору.
     *
     * @param eventId id события.
     * @return событие.
     * @throws NotFoundException если событие не найдено.
     */
    EventEntity findEvent(long eventId);

    /**
     * Создание события.
     *
     * @param userId id пользователя
     * @param event  параметры события
     * @return созданное событие.
     * @throws NotFoundException   если пользователь не найден.
     * @throws ValidationException если переданы некорректные параметры.
     */
    EventEntity createEvent(long userId, EventEntity event);

    /**
     * Обновление события.
     *
     * @param subjectInfo      сведения о вызывающем субъекте
     * @param eventId          id события
     * @param updateParameters параметры для обновления
     * @param stateAction      действие над событием
     * @return обновленное событие.
     * @throws NotFoundException      если событие не найдено.
     * @throws BusinessLogicException если событие не в разрешенном статусе.
     * @throws ValidationException    если переданы некорректные параметры.
     */
    EventEntity updateEvent(SubjectInfo subjectInfo, long eventId,
                            EventUpdateParameters updateParameters, EventStateAction stateAction);

    /**
     * Установка количества уникальных просмотров за всё время.
     *
     * @param eventId id события
     * @param views   количество уникальных просмотров
     */
    void setViewsToEvent(long eventId, long views);
}
