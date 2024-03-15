package ru.practicum.ewm.event.presentation.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.presentation.dto.LocationCreateRequest;
import ru.practicum.ewm.event.presentation.dto.LocationResponse;
import ru.practicum.ewm.event.presentation.dto.LocationUpdateRequest;

@Validated
@RequestMapping("/admin/locations")
@Tag(name = "Admin: Локации", description = "API для работы с локациями")
public interface AdminLocationController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавление новой локации")
    LocationResponse createLocation(@RequestBody @Validated LocationCreateRequest createRequest);

    @PatchMapping("/{locationId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Изменение локации")
    LocationResponse updateLocation(@PathVariable long locationId,
                                    @RequestBody @Validated LocationUpdateRequest updateRequest);

    @DeleteMapping("/{locationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление локации")
    void deleteLocation(@PathVariable long locationId);
}
