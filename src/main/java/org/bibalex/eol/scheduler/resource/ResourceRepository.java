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

//    @Query("select r,p from Resource r INNER JOIN Resource ON r.content_partner_id=p.id where r.id= ?1" +
//            @Query("SELECT r FROM ContentPartner p JOIN p.resources r where  r.id= ?1")
//    public Optional<Resource> findByIdWithCP(long id);


//    @Query(value = "select r.id from Resource r")
////    List<Resource> findAllResourceIDs();
//    ArrayList<Long> findAllResourceIDs();
}
