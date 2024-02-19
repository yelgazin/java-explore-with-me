package ru.practicum.ewm.stat.server.persistence.repository;

import ru.practicum.ewm.stat.server.persistence.dto.StatProjection;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface CustomizedHitRepository {

    List<StatProjection> getStats(LocalDateTime start,
                                  LocalDateTime end,
                                  Collection<String> endpointUris,
                                  boolean unique);
}
