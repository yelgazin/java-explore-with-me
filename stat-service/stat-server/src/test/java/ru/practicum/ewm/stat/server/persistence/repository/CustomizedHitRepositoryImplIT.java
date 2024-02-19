package ru.practicum.ewm.stat.server.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.ewm.stat.server.persistence.dto.StatProjection;
import ru.practicum.ewm.stat.server.persistence.entity.EndpointEntity;
import ru.practicum.ewm.stat.server.persistence.entity.HitEntity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@DataJpaTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class CustomizedHitRepositoryImplIT {

    private final HitRepository hitRepository;
    private final EndpointRepository endpointRepository;

    private EndpointEntity endpoint1;
    private EndpointEntity endpoint2;
    private HitEntity hitEntity11;
    private HitEntity hitEntity12;
    private HitEntity hitEntity21;
    private HitEntity hitEntity22;

    private LocalDateTime startInclusiveBound
            = LocalDateTime.of(2024, 1, 1, 23, 59, 59);
    private LocalDateTime endInclusiveBound
            = LocalDateTime.of(2024, 2, 1, 0, 0, 1);
    private LocalDateTime future = endInclusiveBound.plusDays(10);
    private LocalDateTime past = startInclusiveBound.minusDays(10);

    @BeforeEach
    void setUp() {
        endpoint1 = new EndpointEntity();
        endpoint1.setApp("app1");
        endpoint1.setUri("uri1");

        endpoint2 = new EndpointEntity();
        endpoint2.setApp("app2");
        endpoint2.setUri("uri2");

        hitEntity11 = new HitEntity();
        hitEntity11.setIp("1.1.1.1");
        hitEntity11.setEndpoint(endpoint1);
        hitEntity11.setTimestamp(future);

        hitEntity12 = new HitEntity();
        hitEntity12.setIp("1.1.1.1");
        hitEntity12.setEndpoint(endpoint1);
        hitEntity12.setTimestamp(future);

        hitEntity21 = new HitEntity();
        hitEntity21.setIp("2.2.2.2");
        hitEntity21.setEndpoint(endpoint2);
        hitEntity21.setTimestamp(future);

        hitEntity22 = new HitEntity();
        hitEntity22.setIp("2.2.2.2");
        hitEntity22.setEndpoint(endpoint2);
        hitEntity22.setTimestamp(future);
    }

    @Test
    void getStats_whenHitTimestampEqualsStartBoundary_thenStatWithOneHitReturned() {
        long hitCount = 1;
        hitEntity11.setTimestamp(startInclusiveBound);
        saveAllEntities();
        Collection<String> endpointUris = List.of();

        List<StatProjection> list =
                hitRepository.getStats(startInclusiveBound, endInclusiveBound, endpointUris, false);

        assertThat(list, hasSize(1));
        assertThat(list.get(0).getApp(), equalTo(hitEntity11.getEndpoint().getApp()));
        assertThat(list.get(0).getUri(), equalTo(hitEntity11.getEndpoint().getUri()));
        assertThat(list.get(0).getHits(), equalTo(hitCount));
    }

    @Test
    void getStats_whenHitTimestampEqualsEndBoundary_thenStatWithOneHitReturned() {
        long hitCount = 1;
        hitEntity11.setTimestamp(endInclusiveBound);
        saveAllEntities();
        Collection<String> endpointUris = List.of();

        List<StatProjection> list =
                hitRepository.getStats(startInclusiveBound, endInclusiveBound, endpointUris, false);

        assertThat(list, hasSize(1));
        assertThat(list.get(0).getApp(), equalTo(hitEntity11.getEndpoint().getApp()));
        assertThat(list.get(0).getUri(), equalTo(hitEntity11.getEndpoint().getUri()));
        assertThat(list.get(0).getHits(), equalTo(hitCount));
    }

    @Test
    void getStats_whenHitTimestampBeforeStartBoundary_thenStatWithoutHitReturned() {
        hitEntity11.setTimestamp(past);
        saveAllEntities();
        Collection<String> endpointUris = List.of();

        List<StatProjection> list =
                hitRepository.getStats(startInclusiveBound, endInclusiveBound, endpointUris, false);

        assertThat(list, hasSize(0));
    }

    @Test
    void getStats_whenHitTimestampAfterEndBoundary_thenStatWithoutHitReturned() {
        hitEntity11.setTimestamp(future);
        saveAllEntities();
        Collection<String> endpointUris = List.of();

        List<StatProjection> list =
                hitRepository.getStats(startInclusiveBound, endInclusiveBound, endpointUris, false);

        assertThat(list, hasSize(0));
    }

    @Test
    void getStats_whenHitTimestampInsideBoundary_thenStatWithTwoEndpointAndTwoHitReturned() {
        hitEntity11.setTimestamp(startInclusiveBound);
        hitEntity12.setTimestamp(endInclusiveBound);
        hitEntity21.setTimestamp(startInclusiveBound);
        hitEntity22.setTimestamp(endInclusiveBound);
        long firstEndpointHitCount = 2;
        long secondEndpointHitCount = 2;

        saveAllEntities();
        Collection<String> endpointUris = List.of();

        List<StatProjection> list =
                hitRepository.getStats(startInclusiveBound, endInclusiveBound, endpointUris, false);

        assertThat(list, hasSize(2));
        assertThat(list.get(0).getApp(), equalTo(hitEntity11.getEndpoint().getApp()));
        assertThat(list.get(0).getUri(), equalTo(hitEntity11.getEndpoint().getUri()));
        assertThat(list.get(0).getHits(), equalTo(firstEndpointHitCount));
        assertThat(list.get(1).getApp(), equalTo(hitEntity21.getEndpoint().getApp()));
        assertThat(list.get(1).getUri(), equalTo(hitEntity21.getEndpoint().getUri()));
        assertThat(list.get(1).getHits(), equalTo(secondEndpointHitCount));
    }

    @Test
    void getStats_whenHitTimestampInsideBoundaryAndLimitOneEndpoint_thenStatWithOneEndpointAndTwoHitReturned() {
        hitEntity11.setTimestamp(startInclusiveBound);
        hitEntity12.setTimestamp(endInclusiveBound);
        hitEntity21.setTimestamp(startInclusiveBound);
        hitEntity22.setTimestamp(endInclusiveBound);
        long firstEndpointHitCount = 2;

        saveAllEntities();
        Collection<String> endpointUris = List.of(hitEntity21.getEndpoint().getUri());

        List<StatProjection> list =
                hitRepository.getStats(startInclusiveBound, endInclusiveBound, endpointUris, false);

        assertThat(list, hasSize(1));
        assertThat(list.get(0).getApp(), equalTo(hitEntity21.getEndpoint().getApp()));
        assertThat(list.get(0).getUri(), equalTo(hitEntity21.getEndpoint().getUri()));
        assertThat(list.get(0).getHits(), equalTo(firstEndpointHitCount));
    }

    @Test
    void getStats_whenHitTimestampInsideBoundaryAndRequestUniqueByIp_thenStatWithTwoEndpointAndOneHitReturned() {
        hitEntity11.setTimestamp(startInclusiveBound);
        hitEntity12.setTimestamp(endInclusiveBound);
        hitEntity21.setTimestamp(startInclusiveBound);
        hitEntity22.setTimestamp(endInclusiveBound);
        long firstEndpointHitCount = 1;
        long secondEndpointHitCount = 1;

        saveAllEntities();
        Collection<String> endpointUris = List.of();

        List<StatProjection> list =
                hitRepository.getStats(startInclusiveBound, endInclusiveBound, endpointUris, true);

        assertThat(list, hasSize(2));
        assertThat(list.get(0).getApp(), equalTo(hitEntity11.getEndpoint().getApp()));
        assertThat(list.get(0).getUri(), equalTo(hitEntity11.getEndpoint().getUri()));
        assertThat(list.get(0).getHits(), equalTo(firstEndpointHitCount));
        assertThat(list.get(1).getApp(), equalTo(hitEntity21.getEndpoint().getApp()));
        assertThat(list.get(1).getUri(), equalTo(hitEntity21.getEndpoint().getUri()));
        assertThat(list.get(1).getHits(), equalTo(secondEndpointHitCount));
    }

    private void saveAllEntities() {
        endpointRepository.save(endpoint1);
        endpointRepository.save(endpoint2);
        hitRepository.save(hitEntity11);
        hitRepository.save(hitEntity12);
        hitRepository.save(hitEntity21);
        hitRepository.save(hitEntity22);
    }
}