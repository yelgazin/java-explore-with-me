package ru.practicum.ewm.stat.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewm.stat.client.exception.StatClientException;
import ru.practicum.ewm.stat.common.presentation.dto.EndpointHitCreateRequest;

@Service
public class StatClient {

    private static final String HIT_PATH = "/hit";
    protected final RestTemplate restTemplate;

    @Autowired
    public StatClient(@Value("${stat-server.url}") String serverUrl, RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build();
    }

    public void addEndpointHit(EndpointHitCreateRequest endpointHitCreateRequest) {
        HttpEntity<EndpointHitCreateRequest> requestHttpEntity = new HttpEntity<>(endpointHitCreateRequest);
        ResponseEntity<Void> response;

        try {
            response =
                    restTemplate.exchange(HIT_PATH, HttpMethod.POST, requestHttpEntity, Void.class);
        } catch (Exception ex) {
            throw new StatClientException("Ошибка при сохранении данных.", ex);
        }

        HttpStatus statusCode = response.getStatusCode();
        if (statusCode != HttpStatus.CREATED) {
            throw new StatClientException("Ошибка при сохранении данных. Код ответа от сервера: %s.", statusCode);
        }
    }
}
