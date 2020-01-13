package org.bibalex.eol.scheduler.resource;

import org.bibalex.eol.scheduler.resource.models.LightResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RepositoryRestResource
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    List<LightResource> findByContentPartnerId(long contentPartnerId);
    Optional<LightResource> findById(long id);
    Optional<List<LightResource>> findByIdIn(List<Long> ids);
    List<LightResource> findByContentPartnerId(long contentPartnerId, Pageable pageable);
    Resource findTopByOrderByPositionDesc();
    Resource findTopByOrderByPositionAsc();

    @Query(value = "SELECT * FROM resource WHERE position >= ?1 AND position <= ?2 AND id != ?3",
            nativeQuery = true)
    List<Resource> findByPositionsDownward(int initialPosition, int finalPosition, long resourceID);

    @Query(value = "SELECT * FROM resource WHERE position <= ?1 AND position >= ?2 AND id != ?3",
            nativeQuery = true)
    List<Resource> findByPositionsUpward(int initialPosition, int finalPosition, long resourceID);
}