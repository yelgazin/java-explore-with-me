package ru.practicum.ewm.event.presentation.controller.everyone;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.ewm.event.presentation.dto.EventFullResponse;
import ru.practicum.ewm.event.presentation.dto.EventShortResponse;
import ru.practicum.ewm.event.persistence.enums.EventSortBy;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static ru.practicum.ewm.event.presentation.config.RequestConstants.DEFAULT_PAGE_SIZE;

@Validated
@RequestMapping("/events")
public interface EventController {

    @GetMapping
    List<EventShortResponse> findEvents(@RequestParam(required = false) String text,
                                        @RequestParam(required = false) Collection<Long> categoriesIds,
                                        @RequestParam(required = false) Boolean paid,
                                        @RequestParam(defaultValue = "#{T(java.time.LocalDateTime).now()}")
                                   LocalDateTime rangeStart,
                                        @RequestParam(required = false) LocalDateTime rangeEnd,
                                        @RequestParam(required = false) boolean onlyAvailable,
                                        @RequestParam(required = false) EventSortBy sortBy,
                                        @PositiveOrZero @RequestParam(defaultValue = "0") long from,
                                        @Positive @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int size,
                                        HttpServletRequest request
    );

    @GetMapping("/{id}")
    EventFullResponse findEvent(@PathVariable(name = "id") long eventId, HttpServletRequest request);
}
