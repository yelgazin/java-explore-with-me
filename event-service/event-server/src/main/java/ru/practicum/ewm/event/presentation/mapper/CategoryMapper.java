package ru.practicum.ewm.event.presentation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.practicum.ewm.event.persistence.entity.CategoryEntity;
import ru.practicum.ewm.event.persistence.entity.UserEntity;
import ru.practicum.ewm.event.presentation.dto.CategoryCreateRequest;
import ru.practicum.ewm.event.presentation.dto.CategoryResponse;
import ru.practicum.ewm.event.presentation.dto.CategoryUpdateRequest;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    CategoryResponse toCategoryResponse(CategoryEntity categoryEntity);

    CategoryEntity toCategoryEntity(CategoryCreateRequest categoryCreateRequest);
    CategoryEntity toCategoryEntity(CategoryUpdateRequest categoryUpdateRequest);


    List<CategoryResponse> toCategoryResponse(List<CategoryEntity> categoryEntities);
}

