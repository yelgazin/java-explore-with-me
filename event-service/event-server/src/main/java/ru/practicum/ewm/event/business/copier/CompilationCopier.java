package ru.practicum.ewm.event.business.copier;

import org.mapstruct.*;
import ru.practicum.ewm.event.business.dto.CompilationUpdateParameters;
import ru.practicum.ewm.event.persistence.entity.CompilationEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompilationCopier {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "events", ignore = true)
    void update(@MappingTarget CompilationEntity targetEntity, CompilationUpdateParameters updateParameters);
}
