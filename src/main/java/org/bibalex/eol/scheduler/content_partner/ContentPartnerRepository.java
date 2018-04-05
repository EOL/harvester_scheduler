package org.bibalex.eol.scheduler.content_partner;

import org.bibalex.eol.scheduler.content_partner.models.LightContentPartner;
import org.bibalex.eol.scheduler.resource.Resource;
import org.bibalex.eol.scheduler.resource.models.LightResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;


@RepositoryRestResource
public interface ContentPartnerRepository extends JpaRepository<ContentPartner, Long> {

    public Optional<LightContentPartner> findById(long id);
    public Optional<List<LightContentPartner>> findByIdIn(List<Long> ids);
    public Optional<LightContentPartner> findByResources(List <Resource> c);
//    public Optional<List<ContentPartner>> findById_2(long id);

    @Query("select p from ContentPartner p where p.id = ?1")
    Optional<ContentPartner> findFullContentPartnerById(long id);

}


