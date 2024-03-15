package ru.practicum.ewm.event.business.service.location;

import ru.practicum.ewm.event.business.dto.LocationUpdateParameters;
import ru.practicum.ewm.event.business.exception.NotFoundException;
import ru.practicum.ewm.event.persistence.entity.LocationEntity;

import java.util.List;

/**
 * Сервис локаций.
 */
public interface LocationService {

    /**
     * Создание новой локации.
     *
     * @param locationCreateRequest параметры новой локации
     * @return созданная новая локация.
     */
    LocationEntity createLocation(LocationEntity locationCreateRequest);

    /**
     * Обновление локации.
     * Если поле параметра не указано (равно null) - значит изменение этих данных не требуется.
     *
     * @param locationId       id существующей локации
     * @param updateParameters параметры для обновления
     * @return обновленная локация.
     * @throws NotFoundException если локация не найдена.
     */
    LocationEntity updateLocation(long locationId, LocationUpdateParameters updateParameters);

    /**
     * Удаление локации по id.
     *
     * @param locationId id локации
     * @throws NotFoundException если локация не найдена.
     */
    void deleteLocation(long locationId);

    /**
     * Получение списка локаций.
     *
     * @param from индекс первого объекта
     * @param size максимальное кол-во возвращаемых объектов
     * @return список локаций.
     */
    List<LocationEntity> findLocations(long from, int size);

    /**
     * Получение локации по id.
     *
     * @param locationId id локации.
     * @return найденная локация.
     * @throws NotFoundException если локация не найдена.
     */
    LocationEntity findById(long locationId);
}
