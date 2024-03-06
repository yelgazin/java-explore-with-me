package ru.practicum.ewm.event.business.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.event.business.exception.NotFoundException;
import ru.practicum.ewm.event.persistence.entity.UserEntity;
import ru.practicum.ewm.event.persistence.repository.UserRepository;
import ru.practicum.ewm.event.util.PageableUtil;

import java.util.Collection;
import java.util.List;

import static ru.practicum.ewm.event.util.MessageFormatter.format;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<UserEntity> findUsers(Collection<Long> ids, long from, int size) {
        log.info("Поиск пользователей с идентификаторами {}. Начиная с {}, количество объектов {}.", ids, from, size);

        if (ids.isEmpty()) {
            return userRepository.findAllByOrderById(PageableUtil.of(from, size));
        } else {
            return userRepository.findAllByIdInOrderById(ids, PageableUtil.of(from, size));
        }
    }

    @Override
    public UserEntity createUser(UserEntity user) {
        log.info("Создание пользователя. {}", user);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(long userId) {
        log.info("Удаление пользователя с id {}.", userId);

        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(format("Пользователь с id {} не найден", userId),
                    "Отсутствуют сведения в базе данных");
        }
        userRepository.deleteById(userId);
    }
}
