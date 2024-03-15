package ru.practicum.ewm.event.presentation.mapper;

import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;
import org.mapstruct.*;
import ru.practicum.ewm.event.business.dto.EventUpdateParameters;
import ru.practicum.ewm.event.persistence.entity.EventEntity;
import ru.practicum.ewm.event.presentation.dto.*;

import java.util.List;

import static org.geolatte.geom.builder.DSL.g;
import static org.geolatte.geom.builder.DSL.point;
import static org.geolatte.geom.crs.CoordinateReferenceSystems.WGS84;

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
    @Mapping(source = "location", target = "location", qualifiedByName = "fromPoint")
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
    @Mapping(target = "location", source = "location", qualifiedByName = "toPoint")
    EventEntity toEventEntity(EventCreateRequest createRequest);

    EventUpdateParameters toEventUpdateParameters(EventUpdateRequest updateRequest);

    @Named("toPoint")
    default Point<G2D> toPoint(PointDto pointDto) {
        return point(WGS84, g(pointDto.getLon(), pointDto.getLat()));
    }

    @Named("fromPoint")
    default PointDto toPoint(Point<G2D> point) {
        G2D position = point.getPosition();
        return new PointDto((float) position.getLat(), (float) position.getLon());
    }
}

