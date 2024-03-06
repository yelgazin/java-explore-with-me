package ru.practicum.ewm.event.presentation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.practicum.ewm.event.persistence.entity.ParticipationRequestEntity;
import ru.practicum.ewm.event.presentation.dto.ParticipationRequestResponse;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ParticipationRequestMapper {

    @Mapping(source = "event.id", target = "event")
    @Mapping(source = "requester.id", target = "requester")
    @Mapping(source = "state", target = "status")
    ParticipationRequestResponse toParticipationRequestResponse(ParticipationRequestEntity participationRequest);

    List<ParticipationRequestResponse> toParticipationRequestResponse(
            List<ParticipationRequestEntity> participationRequestEntityList
    );
}
