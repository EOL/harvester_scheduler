package org.bibalex.eol.scheduler.resource;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.stream.Collectors;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import org.bibalex.eol.scheduler.content_partner.ContentPartner;
import org.bibalex.eol.scheduler.content_partner.ContentPartnerService;
import org.bibalex.eol.scheduler.exceptions.NotFoundException;
import org.bibalex.eol.scheduler.harvest.Harvest;
import org.bibalex.eol.scheduler.harvest.HarvestRepository;
import org.bibalex.eol.scheduler.resource.models.LightResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceService {

    private static final Logger logger = LoggerFactory.getLogger(ResourceService.class);

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private HarvestRepository harvestRepository;

    @Autowired
    private ContentPartnerService contentPartnerService;

    @PersistenceContext
    private EntityManager entityManager;

    public long createResource(long contentPartnerId, Resource resource) {
        contentPartnerService.validateContentPartner(contentPartnerId);
        resource.setContentPartner(new ContentPartner(contentPartnerId));

        // reharvest
        // if(resourceRepository.findById(resource.getId()).isPresent()) {
            //  resource.setForced_internally(true);
        //  }

        long resourceID = resourceRepository.save(resource).getId();
        logger.info("Created Resource with ID: " + resourceID);
        return resourceID;
    }

    public Resource updateResource(long contentPartnerId, Long resourceId, Resource resource) {
        contentPartnerService.validateContentPartner(contentPartnerId);
        validateResource(resourceId);
        resource.setContentPartner(new ContentPartner(contentPartnerId));
        resource.setId(resourceId);
        return resourceRepository.save(resource);
    }

    public LightResource getResource(long resourceId) {
        return resourceRepository.findById(resourceId).orElseThrow(() -> new NotFoundException("resource", resourceId));
    }

    public List<LightResource> getResources(long contentPartnerId) {
        contentPartnerService.validateContentPartner(contentPartnerId);
        return resourceRepository.findByContentPartnerId(contentPartnerId);
    }

    public Collection<LightResource> getResources(String resourcesIds) {
        logger.debug("Resources service: get resources with ids : " + resourcesIds);
        if (resourcesIds == null || resourcesIds != null && resourcesIds.length() == 0) {
            logger.error("Exception: Resources not Found");
            throw new NotFoundException("resources ", resourcesIds);
        }
        return resourceRepository.findByIdIn(Arrays.asList(resourcesIds.split("\\s*,\\s*")).stream().map(Long::valueOf).collect(Collectors.toList())).orElseThrow(
                () -> new NotFoundException("resources", resourcesIds));
    }

    public void validateResource(long resourceId) {
        resourceRepository.findById(resourceId).orElseThrow(() -> new NotFoundException("resource", resourceId));
    }

    public LightResource getLightResource(long id) {
        return resourceRepository.findById(id).orElseThrow(() -> new NotFoundException("resource", 1));
    }

    public HashMap<String, Long> getResourceBoundaries() {
        List<Resource> resources = resourceRepository.findAll();
        Long firstID = resources.get(0).getId(),
                lastID = resources.get(resources.size() - 1).getId();
        HashMap<String, Long> resourceLimitIDs = new HashMap<>();
        resourceLimitIDs.put("firstResourceId", firstID);
        resourceLimitIDs.put("lastResourceId", lastID);
        return resourceLimitIDs;
    }

    public ArrayList<HashMap<String, String>> getAllResourcesWithFullData(Long startResourceID, Long endResourceID) {

        //Resource Data: Resource ID, Resource Name, Content Partner ID, Content Partner Name, Last Harvest Status

        ArrayList<HashMap<String, String>> resources = new ArrayList<>();

        while (startResourceID <= endResourceID) {
            Resource res = resourceRepository.findResourceById(startResourceID);
            if (res != null) {
                HashMap<String, String> resourceMap = new HashMap();

                resourceMap.put("resourceID", String.valueOf(res.getId()));
                resourceMap.put("resourceName", res.getName());
                resourceMap.put("contentPartnerID", String.valueOf(res.getContentPartner().getId()));
                resourceMap.put("contentPartnerName", res.getContentPartner().getName());
                resourceMap.put("lastHarvestStatus", getLastHarvestStatus(res.getId()));

                resources.add(resourceMap);
            }
            startResourceID ++;
        }
        return resources;
    }

    public HashMap<String, String> getHarvestHistory(Long resourceID) {
        List<Harvest> harvest = harvestRepository.findByResourceId(resourceID);
        HashMap<String, String> resourceHarvestHistory = new HashMap();

        String harvestMap = "[";
        int i = harvest.size();

        if (!harvest.isEmpty()) {
            for (Harvest harv : harvest) {

                harvestMap += "{\"harvest_id\" : \"" + String.valueOf(harv.getId()) +
                        "\" ,\"startTime\" : \"" + String.valueOf(harv.getStart_at()) +
                        "\" ,\"endTime\" : \"" + String.valueOf(harv.getCompleted_at()) +
                        "\" ,\"status\" : \"" + String.valueOf(harv.getState()) + "\"}";
                i--;
                if (i > 0)
                    harvestMap += ",";
            }

            harvestMap += "]";

            Resource resource = harvest.get(0).getResource();

            String resourceName = resource.getName(),
                    contentPartnerId = String.valueOf(resource.getContentPartner().getId());

            resourceHarvestHistory.put("resourceName", resourceName);
            resourceHarvestHistory.put("contentPartnerId", contentPartnerId);
            resourceHarvestHistory.put("harvestHistory", harvestMap);
        }
        return resourceHarvestHistory;
    }

    String getLastHarvestStatus(Long resourceID) {
        List<Harvest> harvest = harvestRepository.findByResourceId(resourceID);
        HashMap<String, String> lastHarvest = new HashMap();
        String lastHarvestStatus = "";
        if (!harvest.isEmpty()) {
            Harvest harv = harvest.get(harvest.size() - 1);
            lastHarvestStatus = String.valueOf(harv.getState());
        }
        return lastHarvestStatus;
    }

    public boolean checkReadyResources(Timestamp ts) {
        StoredProcedureQuery getResourcesQuery =
                entityManager.createNamedStoredProcedureQuery("getHarvestedResources_sp");

        StoredProcedureQuery storedProcedure =
                getResourcesQuery.setParameter("cDate", ts);

        BigInteger count = (BigInteger) storedProcedure.getSingleResult();
        logger.debug("Number of Ready Resources: " + count.toString());
        return (count.signum() == 1 ? true : false);
    }

    public Long getResourceCount() {
        return resourceRepository.count();
    }

}
