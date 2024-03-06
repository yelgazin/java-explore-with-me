package ru.practicum.ewm.stat.server.business.service;

import ru.practicum.ewm.stat.server.business.dto.GetStatRequestParams;
import ru.practicum.ewm.stat.server.persistence.dto.StatProjection;
import ru.practicum.ewm.stat.server.persistence.entity.HitEntity;

import java.util.List;

public interface StatService {
    HitEntity addHit(HitEntity hitEntity);

    List<StatProjection> getStats(GetStatRequestParams request);
}
