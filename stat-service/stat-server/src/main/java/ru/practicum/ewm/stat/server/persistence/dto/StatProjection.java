package ru.practicum.ewm.stat.server.persistence.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatProjection {

    String app;
    String uri;
    Long hits;
}
