package ru.practicum.ewm.stat.server.presentation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.stat.common.presentation.dto.EndpointHitCreateRequest;
import ru.practicum.ewm.stat.server.persistence.entity.HitEntity;

@Mapper(componentModel = "spring")
public interface HitMapper {

    @Mapping(source = "app", target = "endpoint.app")
    @Mapping(source = "uri", target = "endpoint.uri")
    HitEntity toHitEntity(EndpointHitCreateRequest endpointHitCreateRequest);
}
