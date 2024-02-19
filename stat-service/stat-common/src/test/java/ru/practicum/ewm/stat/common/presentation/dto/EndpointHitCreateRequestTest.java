package ru.practicum.ewm.stat.common.presentation.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.json.JacksonTester;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;

class EndpointHitCreateRequestTest {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private JacksonTester<EndpointHitCreateRequest> jacksonTester;

    @BeforeEach
    void setUp() {
        ObjectMapper objectMapper = new ObjectMapper();
        var module = new JavaTimeModule();
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
        objectMapper.registerModule(module);
        JacksonTester.initFields(this, objectMapper);
    }

    @SneakyThrows
    @Test
    void convertFromJsonToEndpointHitCreateRequest() {
        LocalDateTime timestamp = LocalDateTime.parse("2022-09-06 11:00:23", formatter);

        EndpointHitCreateRequest endpointHitCreateRequest = new EndpointHitCreateRequest();
        endpointHitCreateRequest.setApp("ewm-main-service");
        endpointHitCreateRequest.setUri("/events/1");
        endpointHitCreateRequest.setIp("192.163.0.1");
        endpointHitCreateRequest.setTimestamp(timestamp);

        EndpointHitCreateRequest objectFromJson =
                jacksonTester.readObject("/dto/EndpointHitCreateRequest.json");

        assertThat(objectFromJson, samePropertyValuesAs(endpointHitCreateRequest));
    }
}