package ru.practicum.ewm.event.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm.event.persistence.entity.EventEntity;

import java.time.LocalDateTime;

import static ru.practicum.ewm.event.presentation.config.RequestConstants.FORMAT_DATE_TIME;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventFullResponse extends EventShortResponse {

    @JsonFormat(pattern = FORMAT_DATE_TIME)
    LocalDateTime createdOn;

    PointDto location;
    Integer participantLimit;

    @JsonFormat(pattern = FORMAT_DATE_TIME)
    LocalDateTime publishedOn;

    Boolean requestModeration;
    EventEntity.EventState state;
}
