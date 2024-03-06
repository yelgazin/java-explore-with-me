package ru.practicum.ewm.event.presentation.mapper;

import org.mapstruct.*;
import ru.practicum.ewm.event.persistence.entity.EventEntity;
import ru.practicum.ewm.event.presentation.dto.EventCreateRequest;
import ru.practicum.ewm.event.presentation.dto.EventFullResponse;
import ru.practicum.ewm.event.presentation.dto.EventShortResponse;
import ru.practicum.ewm.event.presentation.dto.EventUpdateRequest;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

    @Named(value = "toEventShortResponse")
    @Mapping(source = "category.id", target = "category")
    @Mapping(source = "date", target = "eventDate")
    EventShortResponse toEventShortResponse(EventEntity eventEntity);

    @IterableMapping(qualifiedByName = "toEventShortResponse")
    List<EventShortResponse> toEventShortResponse(List<EventEntity> eventEntities);

    @Named(value = "toEventFullResponse")
    @Mapping(source = "category.id", target = "category")
    @Mapping(source = "date", target = "eventDate")
    @Mapping(source = "created", target = "createdOn")
    @Mapping(source = "published", target = "publishedOn")
    EventFullResponse toEventFullResponse(EventEntity eventEntity);

    @IterableMapping(qualifiedByName = "toEventFullResponse")
    List<EventFullResponse> toEventFullResponse(List<EventEntity> eventEntities);

    @Mapping(target = "state", constant = "PENDING")
    @Mapping(target = "views", constant = "0L")
    @Mapping(target = "confirmedRequests", constant = "0")
    @Mapping(target = "paid", defaultValue = "false")
    @Mapping(target = "participantLimit", defaultValue = "0")
    @Mapping(target = "requestModeration", defaultValue = "true")
    @Mapping(target = "category.id", source = "category")
    @Mapping(target = "date", source = "eventDate")
    EventEntity toEventEntity(EventCreateRequest createRequest);

    @Mapping(target = "category.id", source = "category")
    @Mapping(target = "date", source = "eventDate")
    EventEntity toEventEntity(EventUpdateRequest updateRequest);
}

