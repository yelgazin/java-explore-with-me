package ru.practicum.ewm.event.presentation.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.presentation.dto.CategoryCreateRequest;
import ru.practicum.ewm.event.presentation.dto.CategoryResponse;
import ru.practicum.ewm.event.presentation.dto.CategoryUpdateRequest;

@Validated
@RequestMapping("/admin/categories")
@Tag(name = "Admin: Категории", description = "API для работы с категориями")
public interface AdminCategoryController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавление новой категории")
    CategoryResponse createCategory(@RequestBody @Validated CategoryCreateRequest createRequest);

    @PatchMapping("/{catId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Изменение категории")
    CategoryResponse updateCategory(@PathVariable(name = "catId") long categoryId,
                                    @RequestBody @Validated CategoryUpdateRequest updateRequest);

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удаление категории")
    void deleteCategory(@PathVariable(name = "catId") long categoryId);
}
