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

    @Mapping(source = "category.id", target = "category")
    @Mapping(source = "date", target = "eventDate")
    EventFullResponse toEventFullResponse(EventEntity eventEntity);

    @IterableMapping(qualifiedByName = "toEventShortResponse")
    List<EventShortResponse> toEventShortResponse(List<EventEntity> eventEntities);

    @Mapping(target = "state", constant = "PENDING")
    @Mapping(target = "views", constant = "0L")
    @Mapping(target = "paid", defaultValue = "false")
    @Mapping(target = "participantLimit", defaultValue = "0")
    @Mapping(target = "requestModeration", defaultValue = "true")
    @Mapping(target = "category.id", source = "category")
    @Mapping(target = "date", source = "eventDate")
    EventEntity toEventEntity(EventCreateRequest createRequest);

    @Mapping(target = "category.id", source = "category")
    @Mapping(target = "date", source = "eventDate")
    EventEntity toEventEntity(EventUpdateRequest updateRequest);

//    @Mapping(target = "status", source = "stateAction", qualifiedByName = "")
//    EventEntity toEventEntity(EventUpdateRequest updateRequest);
//
//    @Named("stat")
//    default EventEntity.EventState stateActionToState(EventUpdateRequest.StateAction stateAction) {
//        switch (stateAction) {
//            case SEND_TO_REVIEW: return EventEntity.EventState.PENDING;
//            case CANCEL_REVIEW: return EventEntity.EventState.CANCELED;
//
//        }
//    }
}

