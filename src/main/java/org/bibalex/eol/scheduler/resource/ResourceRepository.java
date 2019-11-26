package org.bibalex.eol.scheduler.resource;

import java.util.List;
import java.util.Optional;
import org.bibalex.eol.scheduler.resource.models.LightResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ResourceRepository extends JpaRepository<Resource, Long> {

    List<LightResource> findByContentPartnerId(long contentPartnerId);

    Optional<LightResource> findById(long id);

    Resource findResourceById(long id);

    Optional<List<LightResource>> findByIdIn(List<Long> ids);

    //  @Query("select r,p from Resource r INNER JOIN Resource ON r.content_partner_id=p.id where r.id= ?1" +
    //  @Query("SELECT r FROM ContentPartner p JOIN p.resources r where  r.id= ?1")
    //  public Optional<Resource> findByIdWithCP(long id);

}
