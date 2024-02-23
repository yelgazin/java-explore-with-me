package ru.practicum.ewm.event.presentation.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompilationResponse {

    Long id;
    String title;
    Boolean pinned;
    Collection<EventShortResponse> events;
}
