package org.bibalex.eol.scheduler.content_partner;

import java.util.List;
import java.util.Optional;
import org.bibalex.eol.scheduler.content_partner.models.LightContentPartner;
import org.bibalex.eol.scheduler.resource.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ContentPartnerRepository extends JpaRepository<ContentPartner, Long> {

    Optional<LightContentPartner> findById(long id);

    Optional<List<LightContentPartner>> findByIdIn(List<Long> ids);

    Optional<LightContentPartner> findByResources(List<Resource> c);

    ContentPartner findContentPartnerById(long id);

    @Query("select p from ContentPartner p where p.id = ?1")
    Optional<ContentPartner> findFullContentPartnerById(long id);

}


