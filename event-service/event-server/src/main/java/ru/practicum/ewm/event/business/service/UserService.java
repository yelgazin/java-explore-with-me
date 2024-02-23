package ru.practicum.ewm.event.business.service;

import ru.practicum.ewm.event.persistence.entity.UserEntity;

import java.util.Collection;
import java.util.List;

public interface UserService {

    List<UserEntity> findUsers(Collection<Long> ids, int from, int size);

    UserEntity createUser(UserEntity user);

    void deleteUser(long userId);
}
