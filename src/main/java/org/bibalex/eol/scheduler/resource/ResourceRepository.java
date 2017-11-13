package org.bibalex.eol.scheduler.resource;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by sara.mustafa on 4/18/17.
 */
@RepositoryRestResource
public interface ResourceRepository extends CrudRepository<Resource, Long> {
    public List<Resource> findByContentPartnerId(long contentPartnerId);
    public Optional<Resource> findById(long id);



//    @Modifying(clearAutomatically = true)
//    @Query("UPDATE Resource c SET c.name = :name WHERE c.id = :id")
//    public int setLastHarvestedDate(@Param("name") String name, @Param("id") Long id);


//    @Procedure(name = "harvestResource")
//    public List<Resource> x(@Param("cDate") Date inParam1);
}
