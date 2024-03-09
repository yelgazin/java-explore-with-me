package ru.practicum.ewm.event.presentation.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.presentation.dto.UserCreateRequest;
import ru.practicum.ewm.event.presentation.dto.UserResponse;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;
import java.util.List;

import static ru.practicum.ewm.event.presentation.config.RequestConstants.DEFAULT_PAGE_SIZE;

@Validated
@RequestMapping("/admin/users")
@Tag(name = "Admin: Пользователи", description = "API для работы с пользователями")
public interface AdminUserController {

    @GetMapping
    @Operation(summary = "Получение информации о пользователях")
    List<UserResponse> findUsers(@RequestParam(defaultValue = "") Collection<Long> ids,
                                 @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                 @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) @Positive int size);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавление нового пользователя")
    UserResponse createUser(@Validated @RequestBody UserCreateRequest createRequest);

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление пользователя")
    void deleteUser(@PathVariable long userId);
}
