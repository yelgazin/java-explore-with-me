package ru.practicum.ewm.stat.server.presentation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.ewm.stat.common.presentation.dto.EndpointHitCreateRequest;
import ru.practicum.ewm.stat.server.business.service.StatService;
import ru.practicum.ewm.stat.server.persistence.dto.StatProjection;
import ru.practicum.ewm.stat.server.persistence.entity.EndpointEntity;
import ru.practicum.ewm.stat.server.presentation.mapper.HitMapper;
import ru.practicum.ewm.stat.server.presentation.mapper.StatMapper;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {StatControllerImpl.class, StatMapper.class, HitMapper.class})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class StatControllerImplIT {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final StatMapper statMapper;
    private final HitMapper hitMapper;

    @MockBean
    private StatService statService;

    private EndpointHitCreateRequest endpointHitCreateRequest;
    private StatProjection statProjection;
    private EndpointEntity endpointEntity;
    private LocalDateTime timestamp = LocalDateTime.of(2024, 1, 1, 1, 1);

    @BeforeEach
    void setUp() {
        endpointHitCreateRequest = new EndpointHitCreateRequest();
        endpointHitCreateRequest.setApp("app");
        endpointHitCreateRequest.setUri("/1");
        endpointHitCreateRequest.setIp("1.1.1.1");
        endpointHitCreateRequest.setTimestamp(timestamp);

        endpointEntity = new EndpointEntity();
        endpointEntity.setApp(endpointHitCreateRequest.getApp());
        endpointEntity.setUri(endpointEntity.getUri());

        statProjection = new StatProjection();
        statProjection.setApp(endpointHitCreateRequest.getApp());
        statProjection.setUri(endpointHitCreateRequest.getUri());
        statProjection.setHits(1L);
    }

    @Test
    @SneakyThrows
    void addEndpointHit_whenValidEndpointHitCreateRequest_thenExceptionNotOccurred() {
        mockMvc.perform(post("/hit")
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(endpointHitCreateRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    @SneakyThrows
    void addEndpointHit_whenAppIsNull_thenBadRequestReturned() {
        endpointHitCreateRequest.setApp(null);

        mockMvc.perform(post("/hit")
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(endpointHitCreateRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    void addEndpointHit_whenAppIsEmpty_thenBadRequestReturned() {
        endpointHitCreateRequest.setApp("");

        mockMvc.perform(post("/hit")
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(endpointHitCreateRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    void addEndpointHit_whenUrlIsNull_thenBadRequestReturned() {
        endpointHitCreateRequest.setUri(null);

        mockMvc.perform(post("/hit")
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(endpointHitCreateRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    void addEndpointHit_whenUrlIsEmpty_thenBadRequestReturned() {
        endpointHitCreateRequest.setUri("");

        mockMvc.perform(post("/hit")
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(endpointHitCreateRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    void addEndpointHit_whenIpNull_thenBadRequestReturned() {
        endpointHitCreateRequest.setIp(null);

        mockMvc.perform(post("/hit")
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(endpointHitCreateRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    void addEndpointHit_whenIpEmpty_thenBadRequestReturned() {
        endpointHitCreateRequest.setIp("");

        mockMvc.perform(post("/hit")
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(endpointHitCreateRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    void addEndpointHit_whenTimestampNull_thenBadRequestReturned() {
        endpointHitCreateRequest.setTimestamp(null);

        mockMvc.perform(post("/hit")
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(endpointHitCreateRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    void getStats_whenValidParameters_thenCollectionOfStatReturned() {
        String start = "2024-01-01 01:01:01";
        String end = "2024-01-02 01:01:01";

        when(statService.getStats(any()))
                .thenReturn(List.of(statProjection));

        mockMvc.perform(get("/stats")
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("start", start)
                        .param("end", end)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].app", is(statProjection.getApp())))
                .andExpect(jsonPath("$[0].uri", is(statProjection.getUri())))
                .andExpect(jsonPath("$[0].hits", is(statProjection.getHits()), Long.class));
    }

    @Test
    @SneakyThrows
    void getStats_whenStartNotProvided_thenBadRequestReturned() {
        String end = "2024-01-02 01:01:01";

        mockMvc.perform(get("/stats")
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("end", end)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    void getStats_whenStartHasInvalidFormat_thenBadRequestReturned() {
        String start = "2024.01.01 01:01:01";
        String end = "2024-01-02 01:01:01";

        mockMvc.perform(get("/stats")
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("start", start)
                        .param("end", end)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    void getStats_whenEndNotProvided_thenBadRequestReturned() {
        String start = "2024-01-01 01:01:01";

        mockMvc.perform(get("/stats")
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("start", start)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    void getStats_whenEndHasInvalidFormat_thenBadRequestReturned() {
        String start = "2024-01-01 01:01:01";
        String end = "2024.01.02 01:01:01";

        mockMvc.perform(get("/stats")
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("start", start)
                        .param("end", end)
                )
                .andExpect(status().isBadRequest());
    }
}