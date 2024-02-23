package ru.practicum.ewm.event.persistence.repository;

import org.springframework.data.domain.Pageable;
import ru.practicum.ewm.event.business.dto.EventSearchParameters;
import ru.practicum.ewm.event.persistence.entity.EventEntity;
import ru.practicum.ewm.event.persistence.enums.EventSortBy;

import java.util.List;

public interface CustomizedEventRepository {

    List<EventEntity> findEvents(EventSearchParameters searchParameters, EventSortBy sortBy, Pageable pageable);
}
