package ru.practicum.ewm.stat.common.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(name = "EndpointHit")
public class EndpointHitCreateRequest {

    @NotBlank(message = "Идентификатор сервиса не может быть пустым.")
    @Schema(description = "Идентификатор сервиса для которого записывается информация", example = "ewm-main-service")
    String app;

    @NotBlank(message = "URI запроса не может быть пустым.")
    @Schema(description = "URI для которого был осуществлен запрос", example = "/events/1")
    String uri;

    @NotEmpty(message = "IP-адрес пользователя не может быть пустым.")
    @Schema(description = "IP-адрес пользователя, осуществившего запрос", example = "192.163.0.1")
    String ip;

    @NotNull(message = "Дата и время не могут быть пустыми.")
    @Schema(description = "Дата и время, когда был совершен запрос к эндпоинту (в формате \"yyyy-MM-dd HH:mm:ss\")",
            example = "2022-09-06 11:00:23")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime timestamp;
}
