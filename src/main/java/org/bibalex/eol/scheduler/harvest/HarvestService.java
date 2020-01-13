package org.bibalex.eol.scheduler.harvest;

import org.bibalex.eol.scheduler.resource.*;
import org.bibalex.eol.scheduler.utils.PropertiesFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.*;
import java.util.*;
import java.util.PriorityQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


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
//        System.out.println("starting init");
        logger.debug("Calling");
        resourcePriorityQueue = new PriorityQueue<Resource>(new ResourcePositionComparator());
        HarvesterClient harvestClient = new HarvesterClient();
//        harvestClient.setApp(app);

//        System.out.println("after creating priority queue");
        logger.debug("After Creating Priority Queue");
        Date midnight = new Date();
        midnight.setHours(23);
        midnight.setMinutes(30);
        midnight.setSeconds(0);
//        long initialDelay = new Date(midnight.getTime()-System.currentTimeMillis()).getTime();

        logger.debug("Before Initial Delay");
        long initialDelay = (10000); // on minute
//        long initialDelay = (1 * 60  *60); // on minute

//        // every time the scheduled task run fill the queue from the database
        executor.scheduleAtFixedRate(()->{

            logger.info("Getting Resources to Be Harvested from DB");
            StoredProcedureQuery findByYearProcedure =
                    entityManager.createNamedStoredProcedureQuery("harvestResource_sp");

            Date dt = new Date();
            StoredProcedureQuery storedProcedure =
                    findByYearProcedure.setParameter("cDate", dt);

            logger.info("Number of Resources to Be Harvested: " + storedProcedure.getResultList().size());
            storedProcedure.getResultList()
                    .forEach(resource -> {
                        Resource res = (Resource)resource;
                        resourcePriorityQueue.add(res);
                    });

            while(!resourcePriorityQueue.isEmpty()) {
                Resource resource = resourcePriorityQueue.poll();
//                System.out.println("Harvesting resource:" + resource.getId());
                logger.info("Harvesting Resource: " + resource.getId());
                Date startDate = new Date();
                try{
                    logger.debug("Going into Harvesting:");
//                    System.out.println("Going into harvesting:");
                    resource.setHarvestInprogress(true);
                    Harvest.State status = harvestClient.harvestResource(resource.getId() + "");

//                    System.out.println("Harvesting status:" + status);
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
//                    System.out.println("Got new harvest id:" + harvId);
                    logger.debug("New Harvest ID: " + harvId);


                    long resId = resourceRepository.save(resource).getId();
//                    System.out.println("Harvested resource:" + resId);
                    logger.info("Harvested Resource: " + resId);
                } catch (Exception e) {
//                    System.out.println("org.bibalex.eol.scheduler.harvest.init: Harvest thread error: harvesteing resource:" + resource.getId() + "-->" + e.getMessage());
                    logger.error("Exception: Error while Harvesting Resource" + resource.getId());
                    logger.error("Stack Trace: ", e);
                    e.printStackTrace();
                }
            };
//        }, initialDelay , 300000L, TimeUnit.MILLISECONDS);  // delay 5 minutes
        }, initialDelay , 30000L, TimeUnit.MILLISECONDS);  // delay 40 sec
//    }, initialDelay , 86400000L, TimeUnit.MILLISECONDS);
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

    public List<HashMap<String, String>> getHarvests(int state, int offset, int limit) {
        List<HashMap<String, String>> harvestingList = new ArrayList<>();
        Page<Harvest> harvests = null;
        Pageable pageable = new PageRequest(offset, limit);
        switch (state){
            case 1:
                harvests = harvestRepository.findByState(Harvest.State.running, pageable);
                break;
            case 2:
                List<HashMap<String, String>> pendingHarvests = getPendingHarvests();
                return pendingHarvests;
            case 3:
                harvests = harvestRepository.findAll(pageable);
                break;
        }

        for(Harvest harvest : harvests) {
            HashMap<String, String> harvestMap = new HashMap();

            harvestMap.put("harvestID", String.valueOf(harvest.getId()));
            harvestMap.put("resourceID", String.valueOf(harvest.getResource().getId()));
            harvestMap.put("resourceName", harvest.getResource().getName());
            harvestMap.put("contentPartnerID", String.valueOf(harvest.getResource().getContentPartner().getId()));
            harvestMap.put("contentPartnerName", harvest.getResource().getContentPartner().getName());
            harvestMap.put("startTime", String.valueOf(harvest.getStart_at()));
            harvestMap.put("endTime", String.valueOf(harvest.getCompleted_at()));
            harvestMap.put("status", String.valueOf(harvest.getState()));

            harvestingList.add(harvestMap);
        }

        return harvestingList;
    }

    public List<HashMap<String, String>> getPendingHarvests() {
        List<HashMap<String, String>> harvestingList = new ArrayList<>();
        List<Harvest> harvests = harvestRepository.findByState();

        for(Harvest harvest : harvests) {
            HashMap<String, String> harvestMap = new HashMap();

            harvestMap.put("harvestID", String.valueOf(harvest.getId()));
            harvestMap.put("resourceID", String.valueOf(harvest.getResource().getId()));
            harvestMap.put("resourceName", harvest.getResource().getName());
            harvestMap.put("contentPartnerID", String.valueOf(harvest.getResource().getContentPartner().getId()));
            harvestMap.put("contentPartnerName", harvest.getResource().getContentPartner().getName());
            harvestMap.put("startTime", String.valueOf(harvest.getStart_at()));
            harvestMap.put("status", String.valueOf(harvest.getState()));
            harvestMap.put("resourcePosition", String.valueOf(harvest.getResource().getPosition()));

            harvestingList.add(harvestMap);
        }

        return harvestingList;
    }
}
