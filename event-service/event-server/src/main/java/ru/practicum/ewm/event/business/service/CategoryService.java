package ru.practicum.ewm.event.business.service;

import ru.practicum.ewm.event.persistence.entity.CategoryEntity;
import ru.practicum.ewm.event.persistence.entity.UserEntity;
import ru.practicum.ewm.event.presentation.dto.CategoryCreateRequest;

import java.util.Collection;
import java.util.List;

public interface CategoryService {

    CategoryEntity createCategory(CategoryEntity categoryCreateRequest);

    CategoryEntity updateCategory(long categoryId, CategoryEntity category);

    void deleteCategory(long categoryId);

    List<CategoryEntity> findCategories(long from, int size);

    CategoryEntity findById(long categoryId);
}
