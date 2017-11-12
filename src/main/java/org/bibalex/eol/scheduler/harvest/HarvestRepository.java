package org.bibalex.eol.scheduler.harvest;

import org.bibalex.eol.scheduler.resource.Resource;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Date;
import java.util.List;

/**
 * Created by sara.mustafa on 5/2/17.
 */
@RepositoryRestResource
public interface HarvestRepository extends CrudRepository<Harvest, Long>{

    public List<Harvest> findByResourceId(long resourceId);


//    @Modifying
//    @Query("UPDATE Resource u set u.last_harvested_at = ?1 where u.id = ?2")
//    int setLastHarvestedDate(Date last_harvested_at, Long id);

}
