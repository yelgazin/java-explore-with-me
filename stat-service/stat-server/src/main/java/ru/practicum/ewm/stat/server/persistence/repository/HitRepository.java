package ru.practicum.ewm.stat.server.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.ewm.stat.server.persistence.dto.StatProjection;
import ru.practicum.ewm.stat.server.persistence.entity.HitEntity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface HitRepository extends JpaRepository<HitEntity, Long>,
        QuerydslPredicateExecutor<HitEntity>, CustomizedHitRepository {

    List<StatProjection> getStats(LocalDateTime start,
                                  LocalDateTime end,
                                  Collection<String> endpointUris,
                                  boolean unique);
}
