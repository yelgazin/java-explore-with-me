package ru.practicum.ewm.event.business.copier;

import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;
import org.mapstruct.*;
import ru.practicum.ewm.event.business.dto.EventUpdateParameters;
import ru.practicum.ewm.event.persistence.entity.EventEntity;
import ru.practicum.ewm.event.presentation.dto.PointDto;

import static org.geolatte.geom.builder.DSL.g;
import static org.geolatte.geom.builder.DSL.point;
import static org.geolatte.geom.crs.CoordinateReferenceSystems.WGS84;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventCopier {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "location", source = "location", qualifiedByName = "toPoint")
    void update(@MappingTarget EventEntity targetEntity, EventUpdateParameters updateParameters);

    @Named("toPoint")
    default Point<G2D> toPoint(PointDto pointDto) {
        return point(WGS84, g(pointDto.getLon(), pointDto.getLat()));
    }
}
