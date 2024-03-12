package ru.practicum.ewm.event.business.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm.event.business.model.EventStateAction;
import ru.practicum.ewm.event.presentation.dto.PointDto;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventUpdateParameters {

    String title;
    String annotation;
    String description;
    Long category;
    LocalDateTime eventDate;
    PointDto location;
    Boolean paid;
    Integer participantLimit;
    Boolean requestModeration;
    EventStateAction stateAction;
}
