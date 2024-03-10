package ru.practicum.ewm.event.presentation.controller.everyone;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.ewm.event.persistence.enums.EventSortBy;
import ru.practicum.ewm.event.presentation.dto.EventFullResponse;
import ru.practicum.ewm.event.presentation.dto.EventShortResponse;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static ru.practicum.ewm.event.presentation.config.RequestConstants.DEFAULT_PAGE_SIZE;

@Validated
@RequestMapping("/events")
@Tag(name = "Public: События", description = "Публичный API для работы с событиями")
public interface EventController {

    @GetMapping
    @Operation(summary = "Получение событий с возможностью фильтрации")
    List<EventShortResponse> findEvents(
            @RequestParam(required = false) String text,
            @RequestParam(required = false, name = "categories") Collection<Long> categoriesIds,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(defaultValue = "#{T(java.time.LocalDateTime).now()}") LocalDateTime rangeStart,
            @RequestParam(required = false) LocalDateTime rangeEnd,
            @RequestParam(required = false) boolean onlyAvailable,
            @RequestParam(required = false) EventSortBy sortBy,
            @RequestParam(required = false, name = "locations") Collection<Long> locationsIds,
            @PositiveOrZero @RequestParam(defaultValue = "0") long from,
            @Positive @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int size,
            HttpServletRequest request
    );

    @GetMapping("/{id}")
    @Operation(summary = "Получение подробной информации об опубликованном событии по его идентификатору")
    EventFullResponse findEvent(@PathVariable(name = "id") long eventId, HttpServletRequest request);
}
