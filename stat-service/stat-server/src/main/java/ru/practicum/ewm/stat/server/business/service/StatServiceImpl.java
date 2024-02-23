package ru.practicum.ewm.stat.server.business.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.stat.server.business.dto.GetStatRequestParams;
import ru.practicum.ewm.stat.server.persistence.dto.StatProjection;
import ru.practicum.ewm.stat.server.persistence.entity.EndpointEntity;
import ru.practicum.ewm.stat.server.persistence.entity.HitEntity;
import ru.practicum.ewm.stat.server.persistence.repository.EndpointRepository;
import ru.practicum.ewm.stat.server.persistence.repository.HitRepository;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {

    private final HitRepository hitRepository;
    private final EndpointRepository endpointRepository;

    @Override
    public HitEntity addHit(HitEntity hitEntity) {
        log.debug("Сохранение информации о запросе. IP адрес {}.", hitEntity.getIp());

        EndpointEntity endpoint = hitEntity.getEndpoint();
        EndpointEntity savedEndpoint = endpointRepository.findByAppAndUri(endpoint.getApp(), endpoint.getUri())
                .orElseGet(() -> endpointRepository.save(endpoint));

        hitEntity.setEndpoint(savedEndpoint);
        return hitRepository.save(hitEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatProjection> getStats(GetStatRequestParams request) {
        log.debug("Запрос статистики.");
        return hitRepository.getStats(request.getStart(), request.getEnd(), request.getUris(), request.isUnique());
    }
}
