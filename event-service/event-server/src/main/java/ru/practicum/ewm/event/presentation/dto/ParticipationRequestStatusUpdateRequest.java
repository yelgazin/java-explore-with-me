package ru.practicum.ewm.event.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

import static ru.practicum.ewm.event.persistence.entity.ParticipationRequestEntity.ParticipationRequestState;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ParticipationRequestStatusUpdateRequest {

    List<Long> requestIds;
    ParticipationRequestState status;
}
