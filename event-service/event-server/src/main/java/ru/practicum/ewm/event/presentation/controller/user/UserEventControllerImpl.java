package ru.practicum.ewm.event.presentation.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.event.business.model.SubjectInfo;
import ru.practicum.ewm.event.business.model.SubjectRole;
import ru.practicum.ewm.event.business.service.event.EventService;
import ru.practicum.ewm.event.presentation.dto.EventCreateRequest;
import ru.practicum.ewm.event.presentation.dto.EventFullResponse;
import ru.practicum.ewm.event.presentation.dto.EventUpdateRequest;
import ru.practicum.ewm.event.presentation.mapper.EventMapper;

@RestController
@RequiredArgsConstructor
public class UserEventControllerImpl implements UserEventController {

    private final EventService eventService;
    private final EventMapper eventMapper;

    @Override
    public EventFullResponse createEvent(long userId, EventCreateRequest createRequest) {
        return eventMapper.toEventFullResponse(
                eventService.createEvent(SubjectInfo.of(userId, SubjectRole.USER),
                        eventMapper.toEventEntity(createRequest)));
    }

    @Override
    public EventFullResponse updateEvent(long userId, long eventId, EventUpdateRequest updateRequest) {
        return eventMapper.toEventFullResponse(eventService.updateEvent(SubjectInfo.of(userId, SubjectRole.USER),
                eventId, eventMapper.toEventEntity(updateRequest), updateRequest.getStateAction())
        );
    }
}
