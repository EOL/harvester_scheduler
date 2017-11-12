package org.bibalex.eol.scheduler.harvest;

import org.bibalex.eol.scheduler.resource.Resource;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by hduser on 11/9/17.
 */
@Entity
@Table(name = "Resource")

@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "harvestResource", procedureName = "harvestResource", resultClasses = {Resource.class },
        parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "cDate", type = Date.class)
})
        })

public class Test implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}