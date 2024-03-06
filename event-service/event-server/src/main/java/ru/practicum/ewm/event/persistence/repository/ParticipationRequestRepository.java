package ru.practicum.ewm.event.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.event.persistence.entity.ParticipationRequestEntity;

import java.util.List;

@Transactional(readOnly = true)
public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequestEntity, Long> {

    List<ParticipationRequestEntity> findByEventIdAndEventInitiatorId(long eventId, long userId);

    boolean existsByEventIdAndRequesterId(long eventId, long userId);

    List<ParticipationRequestEntity> findByRequesterId(long userId);
}
