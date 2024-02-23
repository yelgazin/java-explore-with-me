package ru.practicum.ewm.event.business.copier;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.ewm.event.persistence.entity.AbstractEntity;

public interface AbstractEntityCopier<T extends AbstractEntity> {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget T entity, T sourceEntity);
}
