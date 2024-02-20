package ru.practicum.ewm.stat.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import ru.practicum.ewm.stat.client.exception.StatClientException;
import ru.practicum.ewm.stat.common.presentation.dto.EndpointHitCreateRequest;

import java.time.LocalDateTime;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatClientTest {

    @Mock
    protected RestTemplate restTemplate;

    @Mock
    protected RestTemplateBuilder restTemplateBuilder;

    private StatClient statClient;
    private EndpointHitCreateRequest endpointHitCreateRequest;
    private LocalDateTime timestamp = LocalDateTime.of(2024, 1, 1, 1, 1);

    @BeforeEach
    void setUp() {
        when(restTemplateBuilder.uriTemplateHandler(any()))
                .thenReturn(restTemplateBuilder);
        when(restTemplateBuilder.requestFactory(ArgumentMatchers.<Supplier<ClientHttpRequestFactory>>any()))
                .thenReturn(restTemplateBuilder);
        when(restTemplateBuilder.build())
                .thenReturn(restTemplate);

        statClient = new StatClient("http://localhost", restTemplateBuilder);

        endpointHitCreateRequest = new EndpointHitCreateRequest();
        endpointHitCreateRequest.setApp("app");
        endpointHitCreateRequest.setUri("/1");
        endpointHitCreateRequest.setIp("1.1.1.1");
        endpointHitCreateRequest.setTimestamp(timestamp);
    }

    @Test
    void addEndpointHit_whenParametersValidAndStatServerAccessible_thenNothingThrown() {

        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class),
                ArgumentMatchers.<Class<Void>>any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CREATED));

        assertDoesNotThrow(() -> statClient.addEndpointHit(endpointHitCreateRequest));
    }

    @Test
    void addEndpointHit_whenStatServerInaccessible_thenStatClientExceptionThrown() {

        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class),
                ArgumentMatchers.<Class<Void>>any()))
                .thenThrow(StatClientException.class);

        assertThrows(StatClientException.class, () -> statClient.addEndpointHit(endpointHitCreateRequest));
    }

    @Test
    void addEndpointHit_whenServerReturnedError_thenStatClientExceptionThrown() {

        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class),
                ArgumentMatchers.<Class<Void>>any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));

        assertThrows(StatClientException.class, () -> statClient.addEndpointHit(endpointHitCreateRequest));
    }
}