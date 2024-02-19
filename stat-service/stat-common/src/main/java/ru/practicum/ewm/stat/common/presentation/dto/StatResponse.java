package ru.practicum.ewm.stat.common.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(name = "ViewStats")
public class StatResponse {

    @Schema(description = "Название сервиса", example = "ewm-main-service")
    String app;

    @Schema(description = "URI сервиса", example = "/events/1")
    String uri;

    @Schema(description = "Количество просмотров", example = "6")
    Long hits;
}
