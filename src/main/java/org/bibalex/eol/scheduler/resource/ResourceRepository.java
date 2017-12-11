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


@RepositoryRestResource
public interface ResourceRepository extends CrudRepository<Resource, Long> {
    public List<Resource> findByContentPartnerId(long contentPartnerId);
    public Optional<Resource> findById(long id);
}
