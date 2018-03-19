package org.bibalex.eol.scheduler.harvest;

import org.bibalex.eol.scheduler.resource.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.*;
import java.io.IOException;
import java.util.*;
import java.util.PriorityQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
//import org.bibalex.eol.harvester.Harvester;
//import org.bibalex.eol.harvseter.*;
//import org.eol.harvester.*;

@Service
public class HarvestService {

    private static final Logger logger = Logger.getLogger(HarvestService.class);
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    @PersistenceContext
    private EntityManager entityManager;
    private PriorityQueue<Resource> resourcePriorityQueue;
    @Autowired
    private HarvestRepository harvestRepository;
    @Autowired
    private ResourceRepository resourceRepository;


    @PostConstruct
    private void init() {
        System.out.println("starting init");
        resourcePriorityQueue = new PriorityQueue<Resource>(new ResourcePositionComparator());
        System.out.println("after creating priority queue");
        Date midnight = new Date();
        midnight.setHours(23);
        midnight.setMinutes(30);
        midnight.setSeconds(0);
//        entityManagerFactory = Persistence.createEntityManagerFactory("mnf-pu");
//        entityManager = entityManagerFactory.createEntityManager();
//        long initialDelay = new Date(midnight.getTime()-System.currentTimeMillis()).getTime();

//        Harvester harv = new Harvester();


        System.out.println("before initial delay");
        long initialDelay = (10000); // on minute
//        long initialDelay = (1 * 60  *60); // on minute

//        // every time the scheduled task run fill the queue from the database
        System.out.println("Before executor");
        executor.scheduleAtFixedRate(() -> {
            System.out.println("Inside executor");
            logger.debug("\nHarvestService get resources to be harvested from DB:");
            System.out.println("b4 SP");//------------------
            Date dt = new Date();

            StoredProcedureQuery findByYearProcedure =
                    entityManager.createNamedStoredProcedureQuery("harvestResource_sp");

            StoredProcedureQuery storedProcedure =
                    findByYearProcedure.setParameter("cDate", dt);
//            storedProcedure.execute();
            System.out.println("after date");

            System.out.println("--->" + storedProcedure.getResultList().size());
//            logger.debug(storedProcedure.getResultList().size());
            System.out.println("b4 result");
//            try {
                storedProcedure.getResultList()
                        .forEach(resource -> {
                            Resource res = (Resource) resource;
                            System.out.println("--->" + res.getName() + "-" + res.getContentPartner().getName() + "-" + res.getHarvest_frequency());
                            resourcePriorityQueue.add(res);
                        });
//            }catch (Exception e){
//                e.printStackTrace();
//            }

            while (!resourcePriorityQueue.isEmpty()) {
                Resource resource = resourcePriorityQueue.poll();
                System.out.println("Harvesting resource:" + resource.getId());
                logger.debug("\nHarvesting resource:" + resource.getId());
                Date startDate = new Date();
//                try {
                logger.debug("Going into harvesting:");
                System.out.println("Going into harvesting:");
                resource.setIs_harvest_inprogress(true);

                HarvesterClient harvesterClient = new HarvesterClient();
                harvesterClient.callHarvester(String.valueOf(resource.getId()));
//                    String status = harv.processHarvesting(resource.getId().intValue());
                String status = "success";
                System.out.println("Harvesting status:" + status);
                logger.debug("\nHarvesting status:" + status);
                Date endDate = new Date();
                resource.setForced_internally(false);
                resource.setLast_harvested_at(endDate);

                Harvest harvest = new Harvest();
                harvest.setResource(resource);
                harvest.setCompleted_at(endDate);
                harvest.setStart_at(startDate);
//                    harvest.setState(harvest.getHarvestStatus(status));
                resource.setIs_harvest_inprogress(false);


                long harvId = harvestRepository.save(harvest).getId();
                System.out.println("Got new harvest id:" + harvId);
                logger.debug("Got new harvest id:" + harvId);


                long resId = resourceRepository.save(resource).getId();
                System.out.println("Harvested resource:" + resId);
                logger.debug("Harvested resource:" + resId);
//                } catch (IOException e) {
//                    System.out.println("org.bibalex.eol.scheduler.harvest.init: Harvest thread error: harvesteing resource:" + resource.getId() + "-->" + e.getMessage());
//                    logger.debug("org.bibalex.eol.scheduler.harvest.init: Harvest thread error: harvesteing resource:" + resource.getId() + "-->" + e.getMessage());
//                    e.printStackTrace();
//                }
            }
            ;
//        }, initialDelay , 300000L, TimeUnit.MILLISECONDS);  // delay 5 minutes
        }, initialDelay, 30000L, TimeUnit.MILLISECONDS);  // delay 30 sec
//    }, initialDelay , 86400000L, TimeUnit.MILLISECONDS);
    }


    @PreDestroy
    private void destroy() {
        try {
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error("executor tasks interrupted");
        } finally {
            if (!executor.isTerminated()) {
                logger.error("cancel non-finished executor tasks");
            }
            executor.shutdownNow();
            logger.error("executor shutdown finished");
        }

    }

}
