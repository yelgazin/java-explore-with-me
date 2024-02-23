package ru.practicum.ewm.event.presentation.controller.everyone;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.event.business.dto.EventSearchParameters;
import ru.practicum.ewm.event.business.model.SubjectInfo;
import ru.practicum.ewm.event.business.model.SubjectRole;
import ru.practicum.ewm.event.business.service.event.EventService;
import ru.practicum.ewm.event.persistence.enums.EventSortBy;
import ru.practicum.ewm.event.presentation.dto.EventFullResponse;
import ru.practicum.ewm.event.presentation.dto.EventShortResponse;
import ru.practicum.ewm.event.presentation.mapper.EventMapper;
import ru.practicum.ewm.stat.client.StatClient;
import ru.practicum.ewm.stat.common.presentation.dto.EndpointHitCreateRequest;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static ru.practicum.ewm.event.presentation.config.RequestConstants.APPLICATION_NAME;

@RestController
@RequiredArgsConstructor
public class EventControllerImpl implements EventController {

    private final EventService eventService;
    private final EventMapper eventMapper;
    private final StatClient statClient;

    @Override
    @Transactional
    public List<EventShortResponse> findEvents(String text, Collection<Long> categoriesIds, Boolean paid,
                                               LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                               boolean onlyAvailable, EventSortBy sortBy, long from, int size,
                                               HttpServletRequest request) {

        EventSearchParameters searchParameters = EventSearchParameters.builder()
                .text(text)
                .categoriesIds(categoriesIds)
                .paid(paid)
                .rangeStart(rangeStart)
                .rangeEnd(rangeEnd)
                .onlyAvailable(onlyAvailable)
                .build();

        List<EventShortResponse> eventEntities = eventMapper.toEventShortResponse(
                eventService.findEvents(SubjectInfo.of(SubjectRole.EVERYONE), searchParameters, sortBy, from, size)
        );

        addToHitStat(request);
        return eventEntities;
    }

    @Override
    @Transactional
    public EventFullResponse findEvent(long eventId, HttpServletRequest request) {

        EventFullResponse foundEvent = eventMapper.toEventFullResponse(
                eventService.findEvent(SubjectInfo.of(SubjectRole.EVERYONE), eventId));

        eventService.addOneViewToEvent(eventId);
        addToHitStat(request);

        return foundEvent;
    }

    private void addToHitStat(HttpServletRequest request) {
        EndpointHitCreateRequest endpointHitCreateRequest = new EndpointHitCreateRequest();
        endpointHitCreateRequest.setIp(request.getRemoteAddr());
        endpointHitCreateRequest.setApp(APPLICATION_NAME);
        endpointHitCreateRequest.setUri(request.getRequestURI());
        endpointHitCreateRequest.setTimestamp(LocalDateTime.now());
        statClient.addEndpointHit(endpointHitCreateRequest);
    }
}
