package ru.practicum.ewm.event.presentation.controller.user;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

@Validated
@RequestMapping("/users/{userId}/requests")
public interface UserRequestController {
}
