package ru.practicum.ewm.stat.client;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.ewm.stat.client.exception.StatClientException;
import ru.practicum.ewm.stat.common.presentation.dto.EndpointHitCreateRequest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Disabled
@SpringBootTest(properties = {"stat-server.url = http://localhost:9090"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class StatClientIT {

    private EndpointHitCreateRequest endpointHitCreateRequest;
    private final StatClient statClient;

    @BeforeEach
    void setUp() {
        endpointHitCreateRequest = new EndpointHitCreateRequest();
        endpointHitCreateRequest.setApp("app");
        endpointHitCreateRequest.setUri("/1");
        endpointHitCreateRequest.setIp("1.1.1.1");
        endpointHitCreateRequest.setTimestamp(LocalDateTime.of(2024, 1, 1, 1, 1));
    }

    @Test
    void addEndpointHit_whenParametersValid_thenExceptionNotThrow() {
        assertDoesNotThrow(() -> statClient.addEndpointHit(endpointHitCreateRequest));
    }

    @Test
    void addEndpointHit_whenTimestampIsNull_thenStatClientExceptionThrows() {
        endpointHitCreateRequest.setTimestamp(null);

        assertThrows(StatClientException.class, () -> statClient.addEndpointHit(endpointHitCreateRequest));
    }

}