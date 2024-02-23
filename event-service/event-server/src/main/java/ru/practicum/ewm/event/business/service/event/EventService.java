package ru.practicum.ewm.event.business.service.event;

import ru.practicum.ewm.event.business.dto.EventSearchParameters;
import ru.practicum.ewm.event.business.model.EventStateAction;
import ru.practicum.ewm.event.business.model.SubjectInfo;
import ru.practicum.ewm.event.persistence.entity.EventEntity;
import ru.practicum.ewm.event.persistence.entity.ParticipationRequestEntity;
import ru.practicum.ewm.event.persistence.enums.EventSortBy;

import java.util.List;

public interface EventService {

    List<EventEntity> findEvents(SubjectInfo subjectInfo, EventSearchParameters searchParameters,
                                 EventSortBy sortBy, long from, int size);

    EventEntity findEvent(SubjectInfo subjectInfo, long eventId);

    EventEntity createEvent(SubjectInfo subjectInfo, EventEntity event);

    EventEntity updateEvent(SubjectInfo subjectInfo, long eventId, EventEntity event, EventStateAction stateAction);

    ParticipationRequestEntity findEventRequests(SubjectInfo subjectInfo, long eventId);

    ParticipationRequestEntity createEventRequest(SubjectInfo subjectInfo,
                                                  long eventId,
                                                  ParticipationRequestEntity participationRequest);

    ParticipationRequestEntity updateEventRequest(SubjectInfo subjectInfo,
                                                  long eventId,
                                                  ParticipationRequestEntity participationRequest);

    void addOneViewToEvent(long eventId);
}
