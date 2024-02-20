package ru.practicum.ewm.stat.server.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import ru.practicum.ewm.stat.server.persistence.entity.EndpointEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class EndpointRepositoryIT {

    private final EndpointRepository endpointRepository;

    private EndpointEntity endpoint1;
    private EndpointEntity endpoint2;

    @BeforeEach
    void setUp() {
        endpoint1 = new EndpointEntity();
        endpoint1.setApp("ewm-event-service");
        endpoint1.setUri("/events/1");

        endpoint2 = new EndpointEntity();
        endpoint2.setApp("ewm-event-service");
        endpoint2.setUri("/events/2");
    }

    @Test
    void findByAppAndUri_whenEndpointWithAppAndUriExists_thenEndpointReturned() {
        endpointRepository.save(endpoint1);
        endpointRepository.save(endpoint2);

        Optional<EndpointEntity> foundEndpoint =
                endpointRepository.findByAppAndUri(endpoint1.getApp(), endpoint1.getUri());

        assertThat(foundEndpoint.isPresent(), is(true));
        assertThat(foundEndpoint.get(), is(endpoint1));
    }

    @Test
    void findByAppAndUri_whenUriNotExists_thenEmptyOptionalReturned() {
        endpointRepository.save(endpoint1);
        String nonExistUri = "/1/2/3";

        Optional<EndpointEntity> foundEndpoint =
                endpointRepository.findByAppAndUri(endpoint1.getApp(), nonExistUri);

        assertThat(foundEndpoint.isPresent(), is(false));
    }

    @Test
    void findByAppAndUri_whenAppNotExists_thenEmptyOptionalReturned() {
        endpointRepository.save(endpoint1);
        String nonExistApp = "/1/2/3";

        Optional<EndpointEntity> foundEndpoint =
                endpointRepository.findByAppAndUri(nonExistApp, endpoint1.getUri());

        assertThat(foundEndpoint.isPresent(), is(false));
    }

    @Test
    void save_whenAppIsNull_thenDataIntegrityViolationExceptionThrown() {
        endpoint1.setApp(null);

        assertThrows(DataIntegrityViolationException.class, () -> endpointRepository.save(endpoint1));
    }

    @Test
    void save_whenUriIsNull_thenDataIntegrityViolationExceptionThrown() {
        endpoint1.setUri(null);

        assertThrows(DataIntegrityViolationException.class, () -> endpointRepository.save(endpoint1));
    }

    @Test
    void save_whenUriAndAppDuplicate_thenDataIntegrityViolationExceptionThrown() {
        endpoint2.setApp(endpoint1.getApp());
        endpoint2.setUri(endpoint1.getUri());

        endpointRepository.save(endpoint1);

        assertThrows(DataIntegrityViolationException.class, () -> endpointRepository.save(endpoint2));
    }

    @Test
    void getAllIdsByUris_whenEndpointsExist_thenListOfIdsReturned() {
        endpointRepository.save(endpoint1);
        endpointRepository.save(endpoint2);

        Collection<Long> ids = endpointRepository.getAllIdsByUris(List.of(endpoint1.getUri(), endpoint2.getUri()));

        assertThat(ids, hasSize(2));
    }

    @Test
    void getAllIdsByUris_whenEndpointsNotExist_thenEmptyListReturned() {
        String nonExistUri = "/1/2/3";

        Collection<Long> ids = endpointRepository.getAllIdsByUris(List.of(nonExistUri));

        assertThat(ids, hasSize(0));
    }
}