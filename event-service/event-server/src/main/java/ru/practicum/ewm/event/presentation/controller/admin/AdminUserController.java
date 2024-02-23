package ru.practicum.ewm.event.presentation.controller.admin;

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
public interface AdminUserController {

    @GetMapping
    List<UserResponse> findUsers(@RequestParam(defaultValue = "") Collection<Long> ids,
                                 @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                 @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) @Positive int size);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UserResponse createUser(@Validated @RequestBody UserCreateRequest createRequest);

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUser(@PathVariable long userId);
}
