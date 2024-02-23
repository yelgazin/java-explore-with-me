package ru.practicum.ewm.event.business.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm.event.persistence.entity.EventEntity;

import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventSearchParameters {

    String text;
    Collection<Long> usersIds;
    Collection<Long> categoriesIds;
    Collection<EventEntity.EventState> states;
    Boolean paid;
    LocalDateTime rangeStart;
    LocalDateTime rangeEnd;
    boolean onlyAvailable;
}
