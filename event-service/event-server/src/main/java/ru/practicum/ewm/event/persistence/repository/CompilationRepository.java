package ru.practicum.ewm.event.persistence.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.event.persistence.entity.CompilationEntity;

import java.util.List;

@Transactional(readOnly = true)
public interface CompilationRepository extends JpaRepository<CompilationEntity, Long> {

    List<CompilationEntity> findAllByPinned(Boolean pinned, Pageable pageable);
}
