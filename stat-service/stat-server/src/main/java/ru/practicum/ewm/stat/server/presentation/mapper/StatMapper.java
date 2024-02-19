package ru.practicum.ewm.stat.server.presentation.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewm.stat.common.presentation.dto.StatResponse;
import ru.practicum.ewm.stat.server.persistence.dto.StatProjection;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StatMapper {

    StatResponse toViewStats(StatProjection statProjection);

    List<StatResponse> toViewStats(List<StatProjection> statProjection);
}
