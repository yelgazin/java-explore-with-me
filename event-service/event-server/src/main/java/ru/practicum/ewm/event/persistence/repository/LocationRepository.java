package ru.practicum.ewm.event.persistence.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.event.persistence.entity.LocationEntity;

import java.util.List;

@Transactional(readOnly = true)
public interface LocationRepository extends JpaRepository<LocationEntity, Long> {

    List<LocationEntity> findAllByOrderById(Pageable pageable);
}
