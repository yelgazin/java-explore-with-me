package ru.practicum.ewm.event.presentation.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.event.business.service.category.CategoryService;
import ru.practicum.ewm.event.presentation.dto.CategoryCreateRequest;
import ru.practicum.ewm.event.presentation.dto.CategoryResponse;
import ru.practicum.ewm.event.presentation.dto.CategoryUpdateRequest;
import ru.practicum.ewm.event.presentation.mapper.CategoryMapper;

@RestController
@RequiredArgsConstructor
public class AdminCategoryControllerImpl implements AdminCategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse createCategory(CategoryCreateRequest createRequest) {
        return categoryMapper.toCategoryResponse(
                categoryService.createCategory(categoryMapper.toCategoryEntity(createRequest))
        );
    }

    @Override
    public CategoryResponse updateCategory(long categoryId, CategoryUpdateRequest updateRequest) {
        return categoryMapper.toCategoryResponse(
                categoryService.updateCategory(categoryId, categoryMapper.toCategoryUpdateParameters(updateRequest))
        );
    }

    @Override
    public void deleteCategory(long categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}
