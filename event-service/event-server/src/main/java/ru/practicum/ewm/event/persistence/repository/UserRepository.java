package ru.practicum.ewm.event.persistence.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.event.persistence.entity.UserEntity;

import java.util.Collection;
import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    List<UserEntity> findAllByIdInOrderById(Collection<Long> ids, Pageable pageable);

    List<UserEntity> findAllByOrderById(Pageable pageable);
}
