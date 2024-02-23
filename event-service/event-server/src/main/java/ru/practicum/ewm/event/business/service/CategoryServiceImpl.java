package ru.practicum.ewm.event.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.event.business.copier.CategoryCopier;
import ru.practicum.ewm.event.business.exception.NotFoundException;
import ru.practicum.ewm.event.persistence.entity.CategoryEntity;
import ru.practicum.ewm.event.persistence.repository.CategoryRepository;
import ru.practicum.ewm.event.util.PageableUtil;

import java.util.List;

import static ru.practicum.ewm.event.util.MessageFormatter.format;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryCopier categoryCopier;

    @Override
    public CategoryEntity createCategory(CategoryEntity category) {
        return categoryRepository.save(category);
    }

    @Override
    public CategoryEntity updateCategory(long categoryId, CategoryEntity category) {
        CategoryEntity savedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(
                        format("Категория с id {} не найдена", categoryId),
                        "Отсутствуют сведения в базе данных"));

        categoryCopier.update(savedCategory, category);
        return categoryRepository.save(savedCategory);
    }

    @Override
    public void deleteCategory(long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new NotFoundException(format("Категория с id {} не найдена", categoryId),
                    "Отсутствуют сведения в базе данных");
        }
        categoryRepository.deleteById(categoryId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryEntity> findCategories(long from, int size) {
        return categoryRepository.findAllByOrderById(PageableUtil.of(from, size));
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryEntity findById(long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(
                        format("Категория с id {} не найдена", categoryId),
                        "Отсутствуют сведения в базе данных"));
    }
}
