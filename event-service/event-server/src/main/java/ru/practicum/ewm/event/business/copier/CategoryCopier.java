package ru.practicum.ewm.event.business.copier;

import org.mapstruct.Mapper;
import ru.practicum.ewm.event.persistence.entity.CategoryEntity;

@Mapper(componentModel = "spring")
public interface CategoryCopier extends AbstractEntityCopier<CategoryEntity> {
}
