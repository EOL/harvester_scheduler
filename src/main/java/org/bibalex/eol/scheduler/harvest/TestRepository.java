package org.bibalex.eol.scheduler.harvest;

import org.bibalex.eol.scheduler.resource.Resource;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by hduser on 11/9/17.
 */
    public interface TestRepository extends CrudRepository<Resource, Long> {

        @Procedure

        public List<Resource> inOnlyTest(@Param("cDate") Date inParam1);



    }
