package ru.practicum.ewm.event.presentation.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.presentation.dto.*;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.ewm.event.presentation.config.RequestConstants.DEFAULT_PAGE_SIZE;

@Validated
@RequestMapping("/users/{userId}/events")
@Tag(name = "Private: События", description = "Закрытый API для работы с событиями")
public interface UserEventController {

    @GetMapping
    @Operation(summary = "Получение событий, добавленных текущим пользователем")
    List<EventShortResponse> findEvents(@PathVariable long userId,
                                        @PositiveOrZero @RequestParam(defaultValue = "0") long from,
                                        @Positive @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int size);

    @GetMapping("/{eventId}")
    @Operation(summary = "Получение полной информации о событии добавленном текущим пользователем")
    EventFullResponse findEvent(@PathVariable long userId,
                                @PathVariable long eventId);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавление нового события")
    EventFullResponse createEvent(@PathVariable long userId, @Validated @RequestBody EventCreateRequest createRequest);

    @PatchMapping("/{eventId}")
    @Operation(summary = "Изменение события добавленного текущим пользователем")
    EventFullResponse updateEvent(@PathVariable long userId,
                                  @PathVariable long eventId,
                                  @Validated @RequestBody EventUpdateRequest updateRequest);

    @GetMapping("/{eventId}/requests")
    @Operation(summary = "Получение информации о запросах на участие в событии текущего пользователя")
    List<ParticipationRequestResponse> findParticipationRequests(@PathVariable long userId,
                                                                 @PathVariable long eventId);

    @PatchMapping("/{eventId}/requests")
    @Operation(summary = "Изменение статуса (подтверждена, отменена) заявок на участие в событии текущего пользователя")
    ResponseEntity<ParticipationRequestStatusUpdateResponse> updateParticipationRequestsStatuses(
            @PathVariable long userId,
            @PathVariable long eventId,
            @RequestBody @Validated ParticipationRequestStatusUpdateRequest updateRequest);
}
