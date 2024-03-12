package ru.practicum.ewm.event.business.copier;

import org.mapstruct.*;
import ru.practicum.ewm.event.business.dto.LocationUpdateParameters;
import ru.practicum.ewm.event.persistence.entity.LocationEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LocationCopier {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget LocationEntity targetEntity, LocationUpdateParameters updateParameters);
}
