package ru.practicum.ewm.event.presentation.controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.presentation.dto.CategoryCreateRequest;
import ru.practicum.ewm.event.presentation.dto.CategoryResponse;
import ru.practicum.ewm.event.presentation.dto.CategoryUpdateRequest;

@Validated
@RequestMapping("/admin/categories")
public interface AdminCategoryController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CategoryResponse createCategory(@Validated @RequestBody CategoryCreateRequest createRequest);

    @PatchMapping("/{catId}")
    @ResponseStatus(HttpStatus.OK)
    CategoryResponse updateCategory(@PathVariable(name = "catId") long categoryId,
                                    @Validated @RequestBody CategoryUpdateRequest updateRequest);

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCategory(@PathVariable(name = "catId") long categoryId);
}
