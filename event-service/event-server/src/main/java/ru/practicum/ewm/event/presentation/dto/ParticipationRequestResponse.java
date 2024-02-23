package ru.practicum.ewm.event.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm.event.business.model.ParticipationRequestState;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ParticipationRequestResponse {

    Long id;
    Long event;
    Long requester;
    LocalDateTime created;
    ParticipationRequestState status;
}
