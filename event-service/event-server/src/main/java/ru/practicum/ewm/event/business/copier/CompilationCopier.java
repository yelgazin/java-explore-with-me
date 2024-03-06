package ru.practicum.ewm.event.business.copier;

import org.mapstruct.Mapper;
import ru.practicum.ewm.event.persistence.entity.CompilationEntity;

@Mapper(componentModel = "spring")
public interface CompilationCopier extends AbstractEntityCopier<CompilationEntity> {
}
