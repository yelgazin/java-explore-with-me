package ru.practicum.ewm.event.presentation.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.presentation.dto.EventCreateRequest;
import ru.practicum.ewm.event.presentation.dto.EventFullResponse;
import ru.practicum.ewm.event.presentation.dto.EventUpdateRequest;

@Validated
@RequestMapping("/users/{userId}/events")
public interface UserEventController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    EventFullResponse createEvent(@PathVariable long userId, @Validated @RequestBody EventCreateRequest createRequest);

    @PatchMapping("/{eventId}")
    EventFullResponse updateEvent(@PathVariable long userId,
                                  @PathVariable long eventId,
                                  @Validated @RequestBody EventUpdateRequest updateRequest);
}
