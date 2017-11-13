package org.bibalex.eol.scheduler.harvest;

import org.bibalex.eol.scheduler.resource.Resource;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by hduser on 11/9/17.
 */

//@Entity
//@Table(name = "Resource")
//@NamedStoredProcedureQuery(
//        name = "harvestResource_sp",
//        procedureName = "harvestResource",
//        parameters = {
//                @StoredProcedureParameter(name = "cDate", mode = ParameterMode.IN, type = Date.class)
//        }
//)

public class Test implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}