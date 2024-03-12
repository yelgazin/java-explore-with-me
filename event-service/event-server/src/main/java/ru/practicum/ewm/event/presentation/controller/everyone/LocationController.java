package ru.practicum.ewm.event.presentation.controller.everyone;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.ewm.event.presentation.dto.LocationResponse;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.ewm.event.presentation.config.RequestConstants.DEFAULT_PAGE_SIZE;

@Validated
@RequestMapping("/locations")
@Tag(name = "Public: Локации", description = "Публичный API для работы с локациями")
public interface LocationController {

    @GetMapping
    @Operation(summary = "Получение локаций")
    List<LocationResponse> findLocations(@RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                         @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) @Positive int size);

    @GetMapping("/{locationId}")
    @Operation(summary = "Получение информации о локации по её идентификатору")
    LocationResponse findLocation(@PathVariable long locationId);
}
