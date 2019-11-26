package org.bibalex.eol.scheduler.harvest;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface HarvestRepository extends CrudRepository<Harvest, Long>{

    List<Harvest> findByResourceId(long resourceId);

}
