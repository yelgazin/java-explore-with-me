package ru.practicum.ewm.event.presentation.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.event.business.service.UserService;
import ru.practicum.ewm.event.presentation.dto.UserCreateRequest;
import ru.practicum.ewm.event.presentation.dto.UserResponse;
import ru.practicum.ewm.event.presentation.mapper.UserMapper;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminUserControllerImpl implements AdminUserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    public List<UserResponse> findUsers(Collection<Long> ids, int from, int size) {
        return userMapper.toUserResponse(userService.findUsers(ids, from, size));
    }

    @Override
    public UserResponse createUser(UserCreateRequest createRequest) {
        return userMapper.toUserResponse(userService.createUser(userMapper.toUserEntity(createRequest)));
    }

    @Override
    public void deleteUser(long userId) {
        userService.deleteUser(userId);
    }
}
