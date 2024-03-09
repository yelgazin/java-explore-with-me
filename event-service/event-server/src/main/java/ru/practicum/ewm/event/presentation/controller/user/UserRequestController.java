package ru.practicum.ewm.event.presentation.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.presentation.dto.ParticipationRequestResponse;

import java.util.List;

@Validated
@RequestMapping("/users/{userId}/requests")
@Tag(name = "Private: Запросы на участие",
        description = "Закрытый API для работы с запросами текущего пользователя на участие в событиях")
public interface UserRequestController {

    @GetMapping
    @Operation(summary = "Получение информации о заявках текущего пользователя на участие в чужих событиях")
    List<ParticipationRequestResponse> findRequests(@PathVariable long userId);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавление запроса от текущего пользователя на участие в событии")
    ParticipationRequestResponse createRequest(@PathVariable long userId,
                                               @RequestParam long eventId);

    @PatchMapping("{requestId}/cancel")
    @Operation(summary = "Отмена своего запроса на участие в событии")
    ParticipationRequestResponse cancelRequest(@PathVariable long userId,
                                               @PathVariable long requestId);
}
