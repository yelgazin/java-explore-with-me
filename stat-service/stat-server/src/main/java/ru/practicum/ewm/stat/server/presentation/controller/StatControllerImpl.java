package ru.practicum.ewm.stat.server.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.stat.common.presentation.dto.EndpointHitCreateRequest;
import ru.practicum.ewm.stat.common.presentation.dto.StatResponse;
import ru.practicum.ewm.stat.server.business.service.StatService;
import ru.practicum.ewm.stat.server.business.dto.GetStatRequestParams;
import ru.practicum.ewm.stat.server.presentation.mapper.HitMapper;
import ru.practicum.ewm.stat.server.presentation.mapper.StatMapper;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatControllerImpl implements StatController {

    private final StatService statService;
    private final StatMapper statMapper;
    private final HitMapper hitMapper;

    @Override
    public void addEndpointHit(EndpointHitCreateRequest endpointHitCreateRequest) {
        statService.addHit(hitMapper.toHitEntity(endpointHitCreateRequest));
    }

    @Override
    public List<StatResponse> getStats(LocalDateTime start,
                                       LocalDateTime end,
                                       Collection<String> uris,
                                       boolean unique) {

        GetStatRequestParams request = GetStatRequestParams.of(start, end, uris, unique);
        return statMapper.toViewStats(statService.getStats(request));
    }
}
