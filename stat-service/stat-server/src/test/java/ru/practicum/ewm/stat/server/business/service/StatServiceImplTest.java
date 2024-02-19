package ru.practicum.ewm.stat.server.business.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.ewm.stat.server.business.dto.GetStatRequestParams;
import ru.practicum.ewm.stat.server.persistence.entity.EndpointEntity;
import ru.practicum.ewm.stat.server.persistence.entity.HitEntity;
import ru.practicum.ewm.stat.server.persistence.repository.EndpointRepository;
import ru.practicum.ewm.stat.server.persistence.repository.HitRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatServiceImplTest {

    @InjectMocks
    private StatServiceImpl statService;

    @Mock
    private HitRepository hitRepository;

    @Mock
    private EndpointRepository endpointRepository;

    private EndpointEntity endpoint;
    private HitEntity hit;

    @BeforeEach
    void setUp() {
        endpoint = new EndpointEntity();
        endpoint.setApp("app");
        endpoint.setUri("/1");

        hit = new HitEntity();
        hit.setEndpoint(endpoint);
        hit.setIp("1.2.3.4");
    }

    @Test
    void addHit_whenEndpointNotExists_thenCalledEndpointCreation() {
        String app = hit.getEndpoint().getApp();
        String uri = hit.getEndpoint().getUri();

        when(endpointRepository.findByAppAndUri(app, uri))
                .thenReturn(Optional.empty());

        statService.addHit(hit);

        verify(endpointRepository).findByAppAndUri(app, uri);
        verify(endpointRepository).save(endpoint);
        verify(hitRepository).save(hit);
    }

    @Test
    void addHit_whenEndpointExists_thenEndpointCreationNotCalled() {
        String app = hit.getEndpoint().getApp();
        String uri = hit.getEndpoint().getUri();

        when(endpointRepository.findByAppAndUri(app, uri))
                .thenReturn(Optional.of(endpoint));

        statService.addHit(hit);

        verify(endpointRepository).findByAppAndUri(app, uri);
        verify(endpointRepository, never()).save(endpoint);
        verify(hitRepository).save(hit);
    }

    @Test
    void getStats_whenAllParametersValid_thenRepositoryGetStatsCalled() {
        GetStatRequestParams params = new GetStatRequestParams();
        Collection<String> uris = List.of(endpoint.getUri());
        params.setUris(uris);

        statService.getStats(params);

        verify(hitRepository).getStats(any(), any(), any(), anyBoolean());
    }

}