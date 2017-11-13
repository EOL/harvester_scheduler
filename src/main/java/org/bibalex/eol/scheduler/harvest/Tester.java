package org.bibalex.eol.scheduler.harvest;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.persistence.*;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.stream.Collectors;

/**
 * Created by hduser on 11/12/17.
 */
@RepositoryRestResource
public class Tester {

//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @Override
//    pubilc List<MyObject> getSomeLegacyData(String firstParameter){
//        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("StoredProcName");
//
//        // Set the parameters of the stored procedure.
//        String firstParam = "firstParam";
//        storedProcedure.registerStoredProcedureParameter(firstParam, String.class, ParameterMode.IN);
//        storedProcedure.setParameter(firstParam, firstParameter);
//
//        // Call the stored procedure.
//        List<Object[]> storedProcedureResults = storedProcedure.getResultList();
//
//        // Use Java 8's cool new functional programming paradigm to map the objects from the stored procedure results
//        return storedProcedureResults.stream().map(result -> new MyObject(
//                (Integer) result[0],
//                (String) result[1]
//        )).collect(Collectors.toList());
//
//    }
}
