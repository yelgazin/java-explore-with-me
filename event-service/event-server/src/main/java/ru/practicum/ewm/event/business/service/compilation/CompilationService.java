package ru.practicum.ewm.event.business.service.compilation;

import ru.practicum.ewm.event.business.exception.NotFoundException;
import ru.practicum.ewm.event.persistence.entity.CompilationEntity;

import java.util.List;

/**
 * Сервис подборок.
 */
public interface CompilationService {

    /**
     * Создание подборки событий.
     *
     * @param compilationEntity параметры подборки
     * @return созданная подборка.
     */
    CompilationEntity createCompilations(CompilationEntity compilationEntity);

    /**
     * Обновление подборки событий.
     * Если поле параметра не указано (равно null) - значит изменение этих данных не требуется.
     *
     * @param compilationId     id существующей подборки
     * @param sourceCompilation параметры для обновления
     * @return обновленная подборка событий.
     */
    CompilationEntity updateCompilation(long compilationId, CompilationEntity sourceCompilation);

    /**
     * Удаление подборки событий.
     *
     * @param compilationId id существующей подборки
     */
    void deleteCompilation(long compilationId);

    /**
     * Поиск подборок по параметрам.
     *
     * @param pinned признак наличия закрепления
     * @param from индекс первого объекта
     * @param size максимальное кол-во возвращаемых объектов
     * @return список подборок.
     */
    List<CompilationEntity> findCompilations(Boolean pinned, long from, int size);

    /**
     * Поиск подборок по параметрам.
     *
     * @param compilationId id подборки
     * @return найденная подборка.
     * @throws NotFoundException если подборка не найдена.
     */
    CompilationEntity findCompilation(long compilationId);
}
