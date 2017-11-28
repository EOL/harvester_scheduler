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
import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.bibalex.eol.harvester.*;

import static org.bibalex.eol.scheduler.resource.Resource.HarvestFrequency.*;


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
        resourcePriorityQueue = new PriorityQueue<Resource>(new ResourcePositionComparator());
        Date midnight = new Date();
        midnight.setHours(23);
        midnight.setMinutes(30);
        midnight.setSeconds(0);
        long initialDelay = new Date(midnight.getTime()-System.currentTimeMillis()).getTime();

        Harvester harv = new Harvester();

        // every time the scheduled task run fill the queue from the database
        executor.scheduleAtFixedRate(()->{
            logger.debug("HarvestService get resources to be harvested from DB:");
            StoredProcedureQuery storedProcedure = entityManager.createNamedStoredProcedureQuery("harvestResource");
            storedProcedure.setParameter("cDate",new Date(), TemporalType.DATE);
            List<Resource> resList = storedProcedure.getResultList();
            resList.forEach( r ->  resourcePriorityQueue.add(r));
            logger.debug("Resources to be harvested:");
            resourcePriorityQueue.stream().forEach(resource -> {
                try {
                    logger.debug("Id:" + resource.getId());
                    Date startDate = new Date();
                    String status = null;
                    status = harv.processHarvesting(resource.getId().intValue());

                    System.out.println("harvest status:" + status);
                    logger.debug("\nharvest status:" + status);
                    Date endDate = new Date();
                    Harvest harvest = new Harvest();
                    harvest.setResource(resource);
                    harvest.setCompleted_at(endDate);
                    harvest.setStart_at(startDate);
                    harvest.setState(getHarvestStatus("succeed"));

                    logger.debug("before save new  harv");
                    System.out.println("before save new  harv");
                    System.out.println("got new harv id:"+harvestRepository.save(harvest).getId());

                    resource.setLast_harvested_at(endDate);

                    //save resource
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        }, initialDelay , 300000L, TimeUnit.MILLISECONDS);  // delay 5 minutes
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
