package ru.practicum.ewm.event.persistence.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.ewm.event.business.dto.EventSearchParameters;
import ru.practicum.ewm.event.persistence.entity.EventEntity;
import ru.practicum.ewm.event.persistence.enums.EventSortBy;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<EventEntity, Long>,
        QuerydslPredicateExecutor<EventEntity>, CustomizedEventRepository {

    Optional<EventEntity> findEventEntityById(long eventId);

    Optional<EventEntity> findEventEntityByInitiatorIdAndId(long userid, long eventId);

    List<EventEntity> findEvents(EventSearchParameters searchParameters, EventSortBy sortBy, Pageable pageable);

    @Query("update EventEntity entity set entity.views = entity.views + 1 where entity.id = :eventId")
    void addOneViewToEvent(long eventId);
}
