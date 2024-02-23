package ru.practicum.ewm.event.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventRequestStatusUpdateResponse {

    Collection<ParticipationRequestResponse> confirmedRequests;
    Collection<ParticipationRequestResponse> rejectedRequests;
}
