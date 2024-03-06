package ru.practicum.ewm.stat.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
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
import ru.practicum.ewm.stat.common.presentation.dto.StatResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Service
public class StatClient {

    private static final String HIT_PATH = "/hit";
    private static final String STATS_PATH = "/stats";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
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
            response = restTemplate.exchange(HIT_PATH, HttpMethod.POST, requestHttpEntity, Void.class);
        } catch (Exception ex) {
            throw new StatClientException("Ошибка при сохранении данных.", ex);
        }

        HttpStatus statusCode = response.getStatusCode();
        if (statusCode != HttpStatus.CREATED) {
            throw new StatClientException("Ошибка при сохранении данных. Код ответа от сервера: %s.", statusCode);
        }
    }

    public List<StatResponse> getStats(LocalDateTime start, LocalDateTime end,
                                       Collection<String> uris, boolean unique) {

        ResponseEntity<List<StatResponse>> response;
        ParameterizedTypeReference<List<StatResponse>> typeRef = new ParameterizedTypeReference<>() {
        };
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("start", start.format(DATE_TIME_FORMATTER));
        parameters.put("end", end.format(DATE_TIME_FORMATTER));
        parameters.put("uris", String.join(",", uris));
        parameters.put("unique", Boolean.toString(unique));

        try {
            response = restTemplate.exchange(STATS_PATH + "?start={start}&end={end}&uris={uris}&unique={unique}",
                    HttpMethod.GET, null, typeRef, parameters);
        } catch (Exception ex) {
            throw new StatClientException("Ошибка при получении данных.", ex);
        }

        HttpStatus statusCode = response.getStatusCode();
        if (statusCode != HttpStatus.OK) {
            throw new StatClientException("Ошибка при получении данных. Код ответа от сервера: %s.", statusCode);
        }

        return response.getBody();
    }
}
