package ru.practicum.ewm.event.business.copier;

import org.mapstruct.*;
import ru.practicum.ewm.event.business.dto.CategoryUpdateParameters;
import ru.practicum.ewm.event.persistence.entity.CategoryEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryCopier {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget CategoryEntity targetEntity, CategoryUpdateParameters updateParameters);
}
