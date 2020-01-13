package org.bibalex.eol.scheduler.harvest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.persistence.NamedQuery;
import java.util.List;


@RepositoryRestResource
public interface HarvestRepository extends JpaRepository<Harvest, Long> {

    List<Harvest> findByResourceId(long resourceId);

    List<Harvest> findByResourceId(Long resourceID, Pageable pageable);

    Page<Harvest> findByState(Harvest.State state, Pageable pageable);

    @Query(value = "SELECT r.*, h.* FROM harvest AS h JOIN resource AS r on r.id = h.resource_id WHERE h.state = 'pending' "
            + "ORDER BY r.position DESC, r.created_at",
    nativeQuery = true)
        List<Harvest> findByState();
}
