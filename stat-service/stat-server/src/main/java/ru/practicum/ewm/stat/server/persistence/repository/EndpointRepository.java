package ru.practicum.ewm.stat.server.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.stat.server.persistence.entity.EndpointEntity;

import java.util.Collection;
import java.util.Optional;

public interface EndpointRepository extends JpaRepository<EndpointEntity, Long> {

    Optional<EndpointEntity> findByAppAndUri(String app, String uri);

    @Query("select endpoint.id from EndpointEntity endpoint where endpoint.uri in :uris")
    Collection<Long> getAllIdsByUris(Collection<String> uris);
}
