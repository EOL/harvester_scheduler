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


@RepositoryRestResource
public interface HarvestRepository extends CrudRepository<Harvest, Long>{

    public List<Harvest> findByResourceId(long resourceId);


//    @Query("update Harvest set media_status= ?2 where resource_id = ?1")
//    @Query("update Harvest set media_status= ?2 where resource_id = ?1")
//    boolean updateMediaStatus(int resource_id, int media_status);
}
