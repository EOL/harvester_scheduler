package org.bibalex.eol.scheduler.content_partner;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

/**
 * Created by sara.mustafa on 4/11/17.
 */
@RepositoryRestResource
public interface ContentPartnerRepository extends CrudRepository<ContentPartner, Long> {
    // , collectionResourceRel = "contentPartner", path = "departments"
    Optional<ContentPartner> findById(long id);
    Optional<List<ContentPartner>> findByIdIn(List<Long> ids);
}


