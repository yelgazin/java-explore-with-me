package ru.practicum.ewm.event.business.service.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.event.business.copier.CategoryCopier;
import ru.practicum.ewm.event.business.exception.BusinessLogicException;
import ru.practicum.ewm.event.business.exception.NotFoundException;
import ru.practicum.ewm.event.persistence.entity.CategoryEntity;
import ru.practicum.ewm.event.persistence.repository.CategoryRepository;
import ru.practicum.ewm.event.persistence.repository.EventRepository;
import ru.practicum.ewm.event.util.PageableUtil;

import java.util.List;

import static ru.practicum.ewm.event.util.MessageFormatter.format;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryCopier categoryCopier;
    private final EventRepository eventRepository;

    @Override
    public CategoryEntity createCategory(CategoryEntity category) {
        log.info("Создание категории. {}.", category);

        return categoryRepository.save(category);
    }

    @Override
    public CategoryEntity updateCategory(long categoryId, CategoryEntity sourceCategory) {
        log.info("Обновление категории c id {}. Параметры обновления {}.", categoryId, sourceCategory);

        CategoryEntity targetCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(
                                format("Категория с id {} не найдена.", categoryId),
                                "Отсутствуют сведения в базе данных."
                        )
                );

        categoryCopier.update(targetCategory, sourceCategory);
        return targetCategory;
    }

    @Override
    public void deleteCategory(long categoryId) {
        log.info("Удаление категории c id {}.", categoryId);

        if (!categoryRepository.existsById(categoryId)) {
            throw new NotFoundException(
                    format("Категория с id {} не найдена.", categoryId),
                    "Отсутствуют сведения в базе данных."
            );
        }

        if (eventRepository.existsByCategoryId(categoryId)) {
            throw new BusinessLogicException(
                    format("Категория с id {} содержит события.", categoryId),
                    "Запрещено удалять категории при наличии связанных событий."
            );
        }

        categoryRepository.deleteById(categoryId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryEntity> findCategories(long from, int size) {
        log.info("Получение категорий начиная с {}, количество объектов {}.", from, size);

        return categoryRepository.findAllByOrderById(PageableUtil.of(from, size));
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryEntity findById(long categoryId) {
        log.info("Получение категорий по id {}.", categoryId);

        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(
                                format("Категория с id {} не найдена.", categoryId),
                                "Отсутствуют сведения в базе данных."
                        )
                );
    }
}
