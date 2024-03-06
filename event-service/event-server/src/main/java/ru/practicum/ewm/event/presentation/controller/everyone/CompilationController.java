package ru.practicum.ewm.event.presentation.controller.everyone;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.ewm.event.presentation.dto.CompilationResponse;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.ewm.event.presentation.config.RequestConstants.DEFAULT_PAGE_SIZE;

@Validated
@RequestMapping("/compilations")
@Tag(name = "Public: Подборки событий", description = "Публичный API для работы с подборками событий")
public interface CompilationController {

    @GetMapping
    @Operation(summary = "Получение подборок событий")
    List<CompilationResponse> findCompilations(@RequestParam(required = false) Boolean pinned,
                                               @PositiveOrZero @RequestParam(defaultValue = "0") long from,
                                               @Positive @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int size);

    @GetMapping("/{compId}")
    @Operation(summary = "Получение подборки событий по его id")
    CompilationResponse findCompilation(@PathVariable(name = "compId") long compilationId);

}
