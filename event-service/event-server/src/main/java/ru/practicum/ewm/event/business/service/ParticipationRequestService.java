package ru.practicum.ewm.event.business.service;

import ru.practicum.ewm.event.business.model.SubjectInfo;
import ru.practicum.ewm.event.persistence.entity.ParticipationRequestEntity;

import java.util.List;

public interface ParticipationRequestService {

    ParticipationRequestEntity findParticipationRequest(long userId, long eventId);

    List<ParticipationRequestEntity> findParticipationRequests(long userId, long eventId);
    List<ParticipationRequestEntity> findParticipationRequests(long userId);

    ParticipationRequestEntity createParticipationRequest(long userId, long eventId,
                                                  ParticipationRequestEntity participationRequest);

    ParticipationRequestEntity updateParticipationRequests(long userId, long eventId,
                                                  ParticipationRequestEntity participationRequest);
}
