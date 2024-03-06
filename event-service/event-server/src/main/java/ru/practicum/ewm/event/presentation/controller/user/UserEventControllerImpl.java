package ru.practicum.ewm.event.presentation.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.event.business.dto.EventSearchParameters;
import ru.practicum.ewm.event.business.model.SubjectInfo;
import ru.practicum.ewm.event.business.model.SubjectRole;
import ru.practicum.ewm.event.business.service.event.EventService;
import ru.practicum.ewm.event.business.service.participation.ParticipationRequestService;
import ru.practicum.ewm.event.persistence.entity.ParticipationRequestEntity;
import ru.practicum.ewm.event.presentation.dto.*;
import ru.practicum.ewm.event.presentation.mapper.EventMapper;
import ru.practicum.ewm.event.presentation.mapper.ParticipationRequestMapper;

import java.util.List;

import static ru.practicum.ewm.event.persistence.entity.ParticipationRequestEntity.ParticipationRequestState.CONFIRMED;

@RestController
@RequiredArgsConstructor
public class UserEventControllerImpl implements UserEventController {

    private final EventService eventService;
    private final EventMapper eventMapper;
    private final ParticipationRequestService participationRequestService;
    private final ParticipationRequestMapper participationRequestMapper;

    @Override
    public List<EventShortResponse> findEvents(long userId, long from, int size) {
        EventSearchParameters eventSearchParameters = EventSearchParameters.builder()
                .usersIds(List.of(userId))
                .build();

        return eventMapper.toEventShortResponse(
                eventService.findEvents(eventSearchParameters, null, from, size)
        );
    }

    @Override
    public EventFullResponse findEvent(long userId, long eventId) {
        return eventMapper.toEventFullResponse(
                eventService.findEventByUserId(userId, eventId)
        );
    }

    @Override
    public EventFullResponse createEvent(long userId, EventCreateRequest createRequest) {
        return eventMapper.toEventFullResponse(
                eventService.createEvent(userId,
                        eventMapper.toEventEntity(createRequest)));
    }

    @Override
    public EventFullResponse updateEvent(long userId, long eventId, EventUpdateRequest updateRequest) {
        return eventMapper.toEventFullResponse(eventService.updateEvent(SubjectInfo.of(userId, SubjectRole.USER),
                eventId, eventMapper.toEventEntity(updateRequest), updateRequest.getStateAction())
        );
    }

    @Override
    public List<ParticipationRequestResponse> findParticipationRequests(long userId, long eventId) {
        return participationRequestMapper.toParticipationRequestResponse(
                participationRequestService.findParticipationRequests(userId, eventId));
    }

    @Override
    public ResponseEntity<ParticipationRequestStatusUpdateResponse> updateParticipationRequestsStatuses(
            long userId,
            long eventId,
            ParticipationRequestStatusUpdateRequest updateRequest) {

        List<List<ParticipationRequestEntity>> lists =
                participationRequestService.updateParticipationRequestsStatuses(userId, eventId,
                        updateRequest.getRequestIds(),
                        updateRequest.getStatus());

        ParticipationRequestStatusUpdateResponse response = new ParticipationRequestStatusUpdateResponse();
        response.setConfirmedRequests(participationRequestMapper.toParticipationRequestResponse(lists.get(0)));
        response.setRejectedRequests(participationRequestMapper.toParticipationRequestResponse(lists.get(1)));

        if (updateRequest.getStatus() == CONFIRMED && !response.getRejectedRequests().isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }
}
