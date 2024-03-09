package ru.practicum.ewm.event.presentation.controller.everyone;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.ewm.event.presentation.dto.CategoryResponse;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.ewm.event.presentation.config.RequestConstants.DEFAULT_PAGE_SIZE;

@Validated
@RequestMapping("/categories")
@Tag(name = "Public: Категории", description = "Публичный API для работы с категориями")
public interface CategoryController {

    @GetMapping
    @Operation(summary = "Получение категорий")
    List<CategoryResponse> findCategories(@RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                          @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) @Positive int size);

    @GetMapping("/{catId}")
    @Operation(summary = "Получение информации о категории по её идентификатору")
    CategoryResponse findCategory(@PathVariable(name = "catId") long categoryId);
}
