package ru.practicum.ewm.event.presentation.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.event.business.dto.EventSearchParameters;
import ru.practicum.ewm.event.business.model.SubjectInfo;
import ru.practicum.ewm.event.business.model.SubjectRole;
import ru.practicum.ewm.event.business.service.event.EventService;
import ru.practicum.ewm.event.persistence.entity.EventEntity;
import ru.practicum.ewm.event.presentation.dto.EventFullResponse;
import ru.practicum.ewm.event.presentation.dto.EventUpdateRequest;
import ru.practicum.ewm.event.presentation.mapper.EventMapper;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminEventControllerImpl implements AdminEventController {

    private final EventService eventService;
    private final EventMapper eventMapper;

    @Override
    public List<EventFullResponse> findEvents(Collection<Long> usersIds,
                                              Collection<EventEntity.EventState> eventsStates,
                                              Collection<Long> categoriesIds,
                                              LocalDateTime rangeStart,
                                              LocalDateTime rangeEnd,
                                              long from,
                                              int size) {

        EventSearchParameters searchParameters = EventSearchParameters.builder()
                .usersIds(usersIds)
                .states(eventsStates)
                .categoriesIds(categoriesIds)
                .rangeStart(rangeStart)
                .rangeEnd(rangeEnd)
                .build();

        return eventMapper.toEventFullResponse(
                eventService.findEvents(searchParameters, null, from, size)
        );
    }

    @Override
    public EventFullResponse updateEvent(long eventId, EventUpdateRequest eventUpdateRequest) {
        return eventMapper.toEventFullResponse(eventService.updateEvent(SubjectInfo.of(SubjectRole.ADMIN),
                eventId, eventMapper.toEventEntity(eventUpdateRequest), eventUpdateRequest.getStateAction())
        );
    }
}
