package org.bibalex.eol.scheduler.harvest;

import java.util.*;
import java.util.PriorityQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.*;
import org.bibalex.eol.scheduler.resource.*;
import org.bibalex.eol.scheduler.utils.PropertiesFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HarvestService {

    private static final Logger logger = LoggerFactory.getLogger(HarvestService.class);


    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    @PersistenceContext
    private EntityManager entityManager;

    private PriorityQueue<Resource> resourcePriorityQueue;

    @Autowired
    private HarvestRepository harvestRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    private PropertiesFile app;

    @Autowired
    public void setApp(PropertiesFile app) {
        this.app = app;
    }

    @PostConstruct
    private void init() {
        logger.debug("Calling");

        resourcePriorityQueue = new PriorityQueue<>(new ResourcePositionComparator());
        HarvesterClient harvestClient = new HarvesterClient();

        logger.debug("After Creating Priority Queue");

        Date midnight = new Date();
        midnight.setHours(23);
        midnight.setMinutes(30);
        midnight.setSeconds(0);

        logger.debug("Before Initial Delay");
        long initialDelay = (10000); // one minute

        // every time the scheduled task run fill the queue from the database
        executor.scheduleAtFixedRate(() -> {
            logger.info("Getting Resources to Be Harvested from DB");
            StoredProcedureQuery findByYearProcedure =
                    entityManager.createNamedStoredProcedureQuery("harvestResource_sp");
            Date date = new Date();
            StoredProcedureQuery storedProcedure =
                    findByYearProcedure.setParameter("cDate", date);
            logger.info("Number of Resources to Be Harvested: " + storedProcedure.getResultList().size());
            storedProcedure.getResultList().forEach(resource -> {
                Resource res = (Resource) resource;
                resourcePriorityQueue.add(res);
            });

            while (!resourcePriorityQueue.isEmpty()) {
                Resource resource = resourcePriorityQueue.poll();
                logger.info("Harvesting Resource: " + resource.getId());
                Date startDate = new Date();
                try {
                    logger.debug("Going into Harvesting:");

                    resource.setHarvestInprogress(true);
                    Harvest.State status = harvestClient.harvestResource(resource.getId() + "");

                    logger.info("Harvest Status: " + status);

                    Date endDate = new Date();
                    resource.setForcedInternally(false);
                    resource.setLastHarvestedAt(endDate);

                    Harvest harvest = new Harvest();
                    harvest.setResource(resource);
                    harvest.setCompleted_at(endDate);
                    harvest.setStart_at(startDate);
                    harvest.setState(status);
                    resource.setHarvestInprogress(false);

                    long harvId = harvestRepository.save(harvest).getId();
                    logger.debug("New Harvest ID: " + harvId);

                    long resId = resourceRepository.save(resource).getId();
                    logger.info("Harvested Resource: " + resId);
                } catch (Exception e) {
                    logger.error("Exception: Error while Harvesting Resource" + resource.getId());
                    logger.error("Stack Trace: ", e);
                    e.printStackTrace();
                }
            }
        }, initialDelay, 30000L, TimeUnit.MILLISECONDS);  // delay 40 sec
    }

    @PreDestroy
    private void destroy() {
        try {
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error("InterruptedException: Executor Tasks Interrupted");
        } finally {
            if (!executor.isTerminated()) {
                logger.error("Cancel Unfinished Executor Tasks");
            }
            executor.shutdownNow();
            logger.error("Executor Shutdown Finished");
        }
    }

}
