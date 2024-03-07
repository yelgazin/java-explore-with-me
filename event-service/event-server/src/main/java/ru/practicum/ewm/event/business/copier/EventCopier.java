package ru.practicum.ewm.event.business.copier;

import org.mapstruct.*;
import ru.practicum.ewm.event.business.dto.EventUpdateParameters;
import ru.practicum.ewm.event.persistence.entity.EventEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventCopier {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "category", ignore = true)
    void update(@MappingTarget EventEntity targetEntity, EventUpdateParameters updateParameters);
}
