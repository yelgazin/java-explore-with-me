package ru.practicum.ewm.event.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static ru.practicum.ewm.event.presentation.config.RequestConstants.FORMAT_DATE_TIME;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventShortResponse {

    Long id;
    String title;
    String annotation;
    String description;
    Long category;
    UserShortResponse initiator;
    Boolean paid;

    @JsonFormat(pattern = FORMAT_DATE_TIME)
    LocalDateTime eventDate;

    Integer confirmedRequests;
    Integer views;
}
