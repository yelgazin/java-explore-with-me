package ru.practicum.ewm.event.business.service.user;

import ru.practicum.ewm.event.business.exception.NotFoundException;
import ru.practicum.ewm.event.persistence.entity.UserEntity;

import java.util.Collection;
import java.util.List;

/**
 * Сервис пользователей.
 */
public interface UserService {

    /**
     * Получение пользователей.
     *
     * @param ids  коллекция идентификаторов пользователей
     * @param from индекс первого объекта
     * @param size максимальное кол-во возвращаемых объектов
     * @return список пользователей.
     */
    List<UserEntity> findUsers(Collection<Long> ids, long from, int size);

    /**
     * Создание пользователя.
     *
     * @param user параметры пользователя
     * @return созданный пользователь.
     */
    UserEntity createUser(UserEntity user);

    /**
     * Удаление пользователя.
     *
     * @param userId id пользователя
     * @throws NotFoundException если пользователь не найден.
     */
    void deleteUser(long userId);
}
