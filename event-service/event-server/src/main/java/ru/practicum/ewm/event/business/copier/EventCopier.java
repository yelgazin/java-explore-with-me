package ru.practicum.ewm.event.business.copier;

import org.mapstruct.Mapper;
import ru.practicum.ewm.event.persistence.entity.EventEntity;

@Mapper(componentModel = "spring")
public interface EventCopier extends AbstractEntityCopier<EventEntity> {
}
