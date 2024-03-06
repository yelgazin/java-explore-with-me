package ru.practicum.ewm.event.presentation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import ru.practicum.ewm.event.persistence.entity.CompilationEntity;
import ru.practicum.ewm.event.persistence.entity.EventEntity;
import ru.practicum.ewm.event.presentation.dto.CompilationCreateRequest;
import ru.practicum.ewm.event.presentation.dto.CompilationResponse;
import ru.practicum.ewm.event.presentation.dto.CompilationUpdateRequest;
import ru.practicum.ewm.event.presentation.dto.EventShortResponse;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompilationMapper {

    @Named(value = "toEventShortResponse")
    @Mapping(source = "category.id", target = "category")
    @Mapping(source = "date", target = "eventDate")
    EventShortResponse toEventShortResponse(EventEntity eventEntity);

    @Mapping(source = "events", target = "events", qualifiedByName = "toEventShortResponse")
    CompilationResponse toCompilationResponse(CompilationEntity compilationEntity);

    List<CompilationResponse> toCompilationResponse(List<CompilationEntity> compilationEntityList);

    @Mapping(source = "pinned", target = "pinned", defaultValue = "false")
    @Mapping(source = "events", target = "events", qualifiedByName = "idsToEvents")
    CompilationEntity toCompilationEntity(CompilationCreateRequest compilationCreateRequest);

    @Mapping(source = "events", target = "events", qualifiedByName = "idsToEvents")
    CompilationEntity toCompilationEntity(CompilationUpdateRequest compilationUpdateRequest);

    @Named(value = "idsToEvents")
    default Set<EventEntity> idsToEvents(Set<Long> events) {
        if (events == null) {
            return new HashSet<>();
        } else {
            return events.stream().map(id -> {
                EventEntity event = new EventEntity();
                event.setId(id);
                return event;
            }).collect(Collectors.toSet());
        }
    }
}
