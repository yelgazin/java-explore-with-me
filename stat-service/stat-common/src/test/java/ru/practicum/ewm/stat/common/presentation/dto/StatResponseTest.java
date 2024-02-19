package ru.practicum.ewm.stat.common.presentation.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.json.JacksonTester;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.samePropertyValuesAs;

class StatResponseTest {

    private JacksonTester<List<StatResponse>> jacksonTester;

    @BeforeEach
    void setUp() {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
    }

    @SneakyThrows
    @Test
    void convertFromJsonToStatResponse() {
        StatResponse statResponse = new StatResponse();
        statResponse.setApp("ewm-main-service");
        statResponse.setUri("/events/1");
        statResponse.setHits(6L);

        List<StatResponse> objectFromJson = jacksonTester.readObject("/dto/StatResponse.json");

        assertThat(objectFromJson, hasSize(1));
        assertThat(objectFromJson.get(0), samePropertyValuesAs(statResponse));
    }
}