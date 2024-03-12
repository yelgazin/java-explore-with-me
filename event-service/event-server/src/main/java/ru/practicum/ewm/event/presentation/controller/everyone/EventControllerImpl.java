package ru.practicum.ewm.event.presentation.controller.everyone;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.event.business.dto.EventSearchParameters;
import ru.practicum.ewm.event.business.service.event.EventService;
import ru.practicum.ewm.event.persistence.entity.EventEntity;
import ru.practicum.ewm.event.persistence.enums.EventSortBy;
import ru.practicum.ewm.event.presentation.dto.EventFullResponse;
import ru.practicum.ewm.event.presentation.dto.EventShortResponse;
import ru.practicum.ewm.event.presentation.mapper.EventMapper;
import ru.practicum.ewm.event.presentation.mapper.LocationMapper;
import ru.practicum.ewm.stat.client.StatClient;
import ru.practicum.ewm.stat.common.presentation.dto.EndpointHitCreateRequest;
import ru.practicum.ewm.stat.common.presentation.dto.StatResponse;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static ru.practicum.ewm.event.presentation.config.RequestConstants.APPLICATION_NAME;

@Slf4j
@RestController
@RequiredArgsConstructor
public class EventControllerImpl implements EventController {

    private static final LocalDateTime EPOCH_START = LocalDateTime.of(1970, 1, 1, 0, 0);
    private final EventService eventService;
    private final EventMapper eventMapper;
    private final LocationMapper locationMapper;
    private final StatClient statClient;

    @Override
    public List<EventShortResponse> findEvents(String text, Collection<Long> categoriesIds, Boolean paid,
                                               LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                               boolean onlyAvailable, EventSortBy sortBy,
                                               Collection<Long> locationsIds, String polygon,
                                               long from, int size, HttpServletRequest request) {

        EventSearchParameters searchParameters = EventSearchParameters.builder()
                .text(text)
                .categoriesIds(categoriesIds)
                .paid(paid)
                .rangeStart(rangeStart)
                .rangeEnd(rangeEnd)
                .onlyAvailable(onlyAvailable)
                .locationsIds(locationsIds)
                .polygon(polygon != null ? locationMapper.toPolygon(polygon) : null)
                .states(List.of(EventEntity.EventState.PUBLISHED))
                .build();

        List<EventShortResponse> eventEntities = eventMapper.toEventShortResponse(
                eventService.findEvents(searchParameters, sortBy, from, size)
        );

        addToHitStat(request);
        return eventEntities;
    }

    @Override
    public EventFullResponse findEvent(long eventId, HttpServletRequest request) {

        addToHitStat(request);
        updateViewsStats(request, eventId);

        return eventMapper.toEventFullResponse(eventService.findEvent(eventId));
    }

    private void addToHitStat(HttpServletRequest request) {
        try {
            EndpointHitCreateRequest endpointHitCreateRequest = new EndpointHitCreateRequest();
            endpointHitCreateRequest.setIp(request.getRemoteAddr());
            endpointHitCreateRequest.setApp(APPLICATION_NAME);
            endpointHitCreateRequest.setUri(request.getRequestURI());
            endpointHitCreateRequest.setTimestamp(LocalDateTime.now());
            statClient.addEndpointHit(endpointHitCreateRequest);
        } catch (Exception ex) {
            log.warn(ex.getLocalizedMessage());
        }
    }

    private void updateViewsStats(HttpServletRequest request, long eventId) {
        try {
            List<StatResponse> list = statClient.getStats(EPOCH_START, LocalDateTime.now(),
                    List.of(request.getRequestURI()), true);

            if (!list.isEmpty()) {
                long hits = list.get(0).getHits();
                eventService.setViewsToEvent(eventId, hits);
            }

        } catch (Exception ex) {
            log.warn(ex.getLocalizedMessage());
        }
    }
}
