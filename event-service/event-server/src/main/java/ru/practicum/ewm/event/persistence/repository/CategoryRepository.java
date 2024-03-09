package ru.practicum.ewm.event.persistence.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.event.persistence.entity.CategoryEntity;

import java.util.List;

@Transactional(readOnly = true)
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    List<CategoryEntity> findAllByOrderById(Pageable pageable);
}
