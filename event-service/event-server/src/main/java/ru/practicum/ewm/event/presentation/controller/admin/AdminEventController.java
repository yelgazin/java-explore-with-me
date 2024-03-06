package ru.practicum.ewm.event.presentation.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.persistence.entity.EventEntity;
import ru.practicum.ewm.event.presentation.dto.EventFullResponse;
import ru.practicum.ewm.event.presentation.dto.EventUpdateRequest;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static ru.practicum.ewm.event.presentation.config.RequestConstants.DEFAULT_PAGE_SIZE;

@Validated
@RequestMapping("/admin/events")
@Tag(name = "Admin: События", description = "API для работы с событиями")
public interface AdminEventController {

    @GetMapping
    @Operation(summary = "Поиск событий")
            List<EventFullResponse> findEvents(
            @RequestParam(required = false, name = "users") Collection<Long> usersIds,
            @RequestParam(required = false, name = "states") Collection<EventEntity.EventState> eventsStates,
            @RequestParam(required = false, name = "categories") Collection<Long> categoriesIds,
            @RequestParam(required = false) LocalDateTime rangeStart,
            @RequestParam(required = false) LocalDateTime rangeEnd,
            @PositiveOrZero @RequestParam(defaultValue = "0") long from,
            @Positive @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int size
    );

    @PatchMapping("/{eventId}")
    @Operation(summary = "Редактирование данных события и его статуса (отклонение/публикация)")
    EventFullResponse updateEvent(@PathVariable long eventId,
                                  @RequestBody @Validated EventUpdateRequest eventUpdateRequest);
}
