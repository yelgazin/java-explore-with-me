package ru.practicum.ewm.event.presentation.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.event.business.service.participation.ParticipationRequestService;
import ru.practicum.ewm.event.presentation.dto.ParticipationRequestResponse;
import ru.practicum.ewm.event.presentation.mapper.ParticipationRequestMapper;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserRequestControllerImpl implements UserRequestController {

    private final ParticipationRequestService participationRequestService;
    private final ParticipationRequestMapper participationRequestMapper;

    @Override
    public List<ParticipationRequestResponse> findRequests(long userId) {
        return participationRequestMapper.toParticipationRequestResponse(
                participationRequestService.findParticipationRequests(userId)
        );
    }

    @Override
    public ParticipationRequestResponse createRequest(long userId, long eventId) {
        return participationRequestMapper.toParticipationRequestResponse(
                participationRequestService.createParticipationRequest(userId, eventId)
        );
    }

    @Override
    public ParticipationRequestResponse cancelRequest(long userId, long requestId) {
        return participationRequestMapper.toParticipationRequestResponse(
                participationRequestService.cancelParticipationRequest(userId, requestId)
        );
    }
}
