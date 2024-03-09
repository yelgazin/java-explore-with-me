package ru.practicum.ewm.event.presentation.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.presentation.dto.CompilationCreateRequest;
import ru.practicum.ewm.event.presentation.dto.CompilationResponse;
import ru.practicum.ewm.event.presentation.dto.CompilationUpdateRequest;

@Validated
@RequestMapping("/admin/compilations")
@Tag(name = "Admin: Подборки событий", description = "API для работы с подборками событий")
public interface AdminCompilationController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавление новой подборки (подборка может не содержать событий)")
    CompilationResponse createCompilation(@RequestBody @Validated CompilationCreateRequest compilationCreateRequest);

    @PatchMapping("/{compId}")
    @Operation(summary = "Обновить информацию о подборке")
    CompilationResponse updateCompilation(@PathVariable(name = "compId") long compilationId,
                                          @RequestBody @Validated CompilationUpdateRequest compilationUpdateRequest);

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление подборки")
    void deleteCompilation(@PathVariable(name = "compId") long compilationId);
}
