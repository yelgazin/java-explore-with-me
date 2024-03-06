package ru.practicum.ewm.event.persistence.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.event.business.dto.EventSearchParameters;
import ru.practicum.ewm.event.persistence.entity.EventEntity;
import ru.practicum.ewm.event.persistence.enums.EventSortBy;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface EventRepository extends JpaRepository<EventEntity, Long>,
        QuerydslPredicateExecutor<EventEntity>, CustomizedEventRepository {

    Optional<EventEntity> findEventEntityById(long eventId);

    Optional<EventEntity> findEventEntityByIdAndState(long eventId, EventEntity.EventState state);


    Optional<EventEntity> findEventEntityByInitiatorIdAndId(long userid, long eventId);

    List<EventEntity> findEvents(EventSearchParameters searchParameters, EventSortBy sortBy, Pageable pageable);

    @Modifying
    @Transactional
    @Query("update EventEntity entity set entity.views = :views where entity.id = :eventId")
    void setViewsToEvent(long eventId, long views);

    boolean existsByCategoryId(long categoryId);
}
