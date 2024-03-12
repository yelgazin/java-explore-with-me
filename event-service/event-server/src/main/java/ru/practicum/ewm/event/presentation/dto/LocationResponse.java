package ru.practicum.ewm.event.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationResponse {

    Long id;
    String name;
    String polygon;
}
