package ru.practicum.ewm.stat.server.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.stat.common.presentation.dto.EndpointHitCreateRequest;
import ru.practicum.ewm.stat.common.presentation.dto.StatResponse;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Validated
@RequestMapping
@Tag(name = "StatsController", description = "API для работы со статистикой посещений")
public interface StatController {

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Сохранение информации о том, что к эндпоинту был запрос",
            description = "Сохранение информации о том, что на uri конкретного сервиса был отправлен запрос " +
                    "пользователем. Название сервиса, uri и ip пользователя указаны в теле запроса.")
    void addEndpointHit(@Validated @RequestBody EndpointHitCreateRequest endpointHit);

    @GetMapping("/stats")
    @Operation(summary = "Получение статистики по посещениям.")
    List<StatResponse> getStats(
            @Parameter(description = "Дата и время начала диапазона за который нужно выгрузить статистику " +
                    "(в формате \"yyyy-MM-dd HH:mm:ss\")")
            @RequestParam LocalDateTime start,

            @Parameter(description = "Дата и время конца диапазона за который нужно выгрузить статистику " +
                    "(в формате \"yyyy-MM-dd HH:mm:ss\")")
            @RequestParam LocalDateTime end,

            @Parameter(description = "Список uri для которых нужно выгрузить статистику")
            @RequestParam(defaultValue = "") Collection<String> uris,

            @Parameter(description = "Нужно ли учитывать только уникальные посещения (только с уникальным ip)")
            @RequestParam(defaultValue = "false") boolean unique);
}
