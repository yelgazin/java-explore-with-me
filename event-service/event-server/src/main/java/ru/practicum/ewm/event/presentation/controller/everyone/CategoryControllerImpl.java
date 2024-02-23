package ru.practicum.ewm.event.presentation.controller.everyone;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.event.business.service.CategoryService;
import ru.practicum.ewm.event.presentation.dto.CategoryResponse;
import ru.practicum.ewm.event.presentation.mapper.CategoryMapper;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryControllerImpl implements CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponse> findCategories(int from, int size) {
        return categoryMapper.toCategoryResponse(categoryService.findCategories(from, size));
    }

    @Override
    public CategoryResponse findCategory(long categoryId) {
        return categoryMapper.toCategoryResponse(categoryService.findById(categoryId));
    }
}
