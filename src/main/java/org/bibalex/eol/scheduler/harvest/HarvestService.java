package org.bibalex.eol.scheduler.harvest;

import org.bibalex.eol.scheduler.resource.Resource;
import org.bibalex.eol.scheduler.resource.ResourcePositionComparator;
import org.bibalex.eol.scheduler.resource.ResourceRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.*;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.bibalex.eol.harvester.*;

import static org.bibalex.eol.scheduler.resource.Resource.HarvestFrequency.*;


/**
 * Created by sara.mustafa on 5/2/17.
 */
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
    private TestRepository testRepository;

    @Autowired
    private ResourceRepository resourceRepository;


    @PostConstruct
    private void init() {
        resourcePriorityQueue = new PriorityQueue<Resource>(new ResourcePositionComparator());
        Date midnight = new Date();
        midnight.setHours(23);
        midnight.setMinutes(30);
        midnight.setSeconds(0);
//        long initialDelay = new Date(midnight.getTime()-System.currentTimeMillis()).getTime();

        Harvester harv = new Harvester();
        long initialDelay = (6000); // on minute
//        long initialDelay = (1 * 60  *60); // on minute

//        // every time the scheduled task run fill the queue from the database
        executor.scheduleAtFixedRate(()->{
            System.out.println("HarvestService get resources to be harvested from DB:");
//            StoredProcedureQuery storedProcedure = entityManager.createNamedStoredProcedureQuery("harvestResource");
//            storedProcedure.setParameter("cDate",new Date(), TemporalType.DATE);
//            storedProcedure.execute();

//            Query storedProcedure = entityManager.createNativeQuery("select * from resource;",
//                    Resource.class)
//                    ;


            System.out.println("b4 SP");
            List<Resource> resList = testRepository.inOnlyTest(new Date());

//            List<Resource> resList = storedProcedure.getResultList();
            System.out.println("aftr SP");
            System.out.println("Size of returned resources" + resList.size());

            resList.forEach( r ->  {System.out.println(r.getId());resourcePriorityQueue.add(r);});
            logger.debug("Resources to be harvested:");
            System.out.println("resourcePriorityQueue size:"  +resourcePriorityQueue.size());
            while(!resourcePriorityQueue.isEmpty()) {
                Resource resource = resourcePriorityQueue.poll();
                System.out.println("Harvesting res:" + resource.getId());
                Date startDate = new Date();
//                logger.debug("Harvesting REsource Id:" + resource.getId());
//                resourcePriorityQueue.poll();
//                System.out.println("Harvesting: " + harv.test(resource.getId()));
                try {
                    logger.debug("testing");
                    logger.debug("harvest status:" + harv.processHarvesting(resource.getId().intValue()));
                    Date endDate = new Date();
                    Harvest harvest = new Harvest();
                    harvest.setResource(resource);
                    harvest.setCompleted_at(endDate);
                    harvest.setStart_at(startDate);
                    harvest.setState(getHarvestStatus("succeed"));


                    System.out.println("got new harv id:"+harvestRepository.save(harvest).getId());

                    resource.setLast_harvested_at(endDate);
    //                System.out.println("updated" + resourceRepository.setLastHarvestedDate("name", resource.getId()));
                    System.out.println("updated:" +resourceRepository.save(resource));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            };
//
//        }, initialDelay , 300000L, TimeUnit.MILLISECONDS);  // delay 5 minutes
        }, initialDelay , 30000L, TimeUnit.MILLISECONDS);  // delay 30 sec
//    }, initialDelay , 86400000L, TimeUnit.MILLISECONDS);
    }

    private Harvest.State getHarvestStatus(String harvestStr) {
        switch (harvestStr) {
            case "succeed":
                return Harvest.State.succeed;
            case "failed":
                return Harvest.State.failed;
            case "running":
                return Harvest.State.running;
            case "pending":
                return Harvest.State.pending;
            default:
                throw new IllegalArgumentException("Unknown " + harvestStr);
        }
    }

    @PreDestroy
    private void destroy(){
        try {
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);
        }
        catch (InterruptedException e) {
            logger.error("executor tasks interrupted");
        }
        finally {
            if (!executor.isTerminated()) {
                logger.error("cancel non-finished executor tasks");
            }
            executor.shutdownNow();
            logger.error("executor shutdown finished");
        }

    }

}
