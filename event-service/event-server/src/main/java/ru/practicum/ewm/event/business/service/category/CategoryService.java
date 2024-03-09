package ru.practicum.ewm.event.business.service.category;

import ru.practicum.ewm.event.business.dto.CategoryUpdateParameters;
import ru.practicum.ewm.event.business.exception.BusinessLogicException;
import ru.practicum.ewm.event.business.exception.NotFoundException;
import ru.practicum.ewm.event.persistence.entity.CategoryEntity;

import java.util.List;

/**
 * Сервис категорий.
 */
public interface CategoryService {

    /**
     * Создание новой категории.
     *
     * @param categoryCreateRequest параметры новой категории
     * @return созданная новая категория.
     */
    CategoryEntity createCategory(CategoryEntity categoryCreateRequest);

    /**
     * Обновление категории.
     * Если поле параметра не указано (равно null) - значит изменение этих данных не требуется.
     *
     * @param categoryId     id существующей категории
     * @param updateParameters параметры для обновления
     * @return обновленная категория.
     * @throws NotFoundException если категория не найдена.
     */
    CategoryEntity updateCategory(long categoryId, CategoryUpdateParameters updateParameters);

    /**
     * Удаление категории по id.
     *
     * @param categoryId id категории
     * @throws NotFoundException      если категория не найдена.
     * @throws BusinessLogicException если существуют события связанные с удаляемой категорией.
     */
    void deleteCategory(long categoryId);

    /**
     * Получение списка категорий.
     *
     * @param from индекс первого объекта
     * @param size максимальное кол-во возвращаемых объектов
     * @return список категорий.
     */
    List<CategoryEntity> findCategories(long from, int size);

    /**
     * Получение категории по id.
     *
     * @param categoryId id категории.
     * @return найденная категория.
     * @throws NotFoundException если категория не найдена.
     */
    CategoryEntity findById(long categoryId);
}
