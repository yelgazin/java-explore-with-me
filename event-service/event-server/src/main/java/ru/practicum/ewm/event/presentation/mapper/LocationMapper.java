package ru.practicum.ewm.event.presentation.mapper;

import org.geolatte.geom.G2D;
import org.geolatte.geom.Polygon;
import org.geolatte.geom.codec.Wkt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import ru.practicum.ewm.event.business.dto.LocationUpdateParameters;
import ru.practicum.ewm.event.persistence.entity.LocationEntity;
import ru.practicum.ewm.event.presentation.dto.LocationCreateRequest;
import ru.practicum.ewm.event.presentation.dto.LocationResponse;
import ru.practicum.ewm.event.presentation.dto.LocationUpdateRequest;

import java.util.List;

import static org.geolatte.geom.crs.CoordinateReferenceSystems.WGS84;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LocationMapper {

    int SRID_TEXT_LENGTH = 10;

    @Mapping(target = "polygon", source = "polygon", qualifiedByName = "fromWkt")
    LocationResponse toLocationResponse(LocationEntity locationEntity);

    @Mapping(target = "polygon", source = "polygon", qualifiedByName = "toWkt")
    LocationEntity toLocationEntity(LocationCreateRequest locationCreateRequest);

    @Mapping(target = "polygon", source = "polygon", qualifiedByName = "toWkt")
    LocationUpdateParameters toLocationUpdateParameters(LocationUpdateRequest locationUpdateRequest);

    List<LocationResponse> toLocationResponse(List<LocationEntity> categoryEntities);

    @Named("toWkt")
    default Polygon<G2D> toPolygon(String polygon) {
        return (Polygon<G2D>) Wkt.fromWkt(polygon, WGS84);
    }

    @Named("fromWkt")
    default String fromPolygon(Polygon<G2D> polygon) {
        return Wkt.toWkt(polygon).substring(SRID_TEXT_LENGTH);
    }
}

