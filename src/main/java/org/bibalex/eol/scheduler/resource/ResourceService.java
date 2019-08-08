package org.bibalex.eol.scheduler.resource;

import org.apache.log4j.Logger;
import org.bibalex.eol.scheduler.content_partner.ContentPartner;
import org.bibalex.eol.scheduler.content_partner.ContentPartnerService;
import org.bibalex.eol.scheduler.content_partner.models.LightContentPartner;
import org.bibalex.eol.scheduler.exceptions.NotFoundException;
import org.bibalex.eol.scheduler.harvest.Harvest;
import org.bibalex.eol.scheduler.harvest.HarvestRepository;
import org.bibalex.eol.scheduler.resource.models.LightResource;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.awt.print.Pageable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class ResourceService {

    private static final Logger logger = Logger.getLogger(ResourceService.class);
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
//        if(resourceRepository.findById(resource.getId()).isPresent()) {
//            resource.setForced_internally(true);
//        }
        return resourceRepository.save(resource).getId();
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

//    public Resource getCompleteResource(long resourceId){
//        return resourceRepository.findAllById(resourceId).orElseThrow(() -> new NotFoundException("resource", resourceId));
//    }

    public List<LightResource> getResources(long contentPartnerId) {
        contentPartnerService.validateContentPartner(contentPartnerId);
        return resourceRepository.findByContentPartnerId(contentPartnerId);
    }

    public void validateResource(long resourceId) {
        resourceRepository.findById(resourceId).orElseThrow(() -> new NotFoundException("resource", resourceId));
    }

    public boolean checkReadyResources(Timestamp ts) {
        StoredProcedureQuery getResourcesQuery =
                entityManager.createNamedStoredProcedureQuery("getHarvestedResources_sp");

        StoredProcedureQuery storedProcedure =
                getResourcesQuery.setParameter("cDate", ts);

        BigInteger count = (BigInteger) storedProcedure.getSingleResult();
        logger.debug("Number of Ready Resources: " + count.toString());
//        System.out.println("checkReadyResources: " + count);
        return (count.signum() == 1 ? true : false);
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

    public LightResource getLightResource(long id) {
        return resourceRepository.findById(id).orElseThrow(() -> new NotFoundException("resource", 1));
    }

    public Long getResourceCount() {
        return resourceRepository.count();
    }

    public ArrayList<HashMap<String, String>> getAllResourcesWithFullData() {

        //Resource Data: Resource ID, Resource Name, Content Partner ID, Content Partner Name, Last Harvest Status

        ArrayList<HashMap<String, String>> resources = new ArrayList<>();

        for (Long res : resourceRepository.findAllResourceIDs()) {

            HashMap<String, String> resourceMap = new HashMap();
            LightResource resource = getResource(res);
            String resourceName = resource.getName();
            LightContentPartner lightContentPartner = contentPartnerService.getResourceCP(res);
            String contentPartnerID = String.valueOf(lightContentPartner.getId());
            String contentPartnerName = lightContentPartner.getName();
            List<Harvest> harvest = harvestRepository.findByResourceId(res);
//            String lastHarvestStatus = String.valueOf(harvest.get(harvest.size() - 1).getState());
            String lastHarvestStatus = getLastHarvest(res).get("status");

            resourceMap.put("resourceID", res.toString());
            resourceMap.put("resourceName", resourceName);
            resourceMap.put("contentPartnerID", contentPartnerID);
            resourceMap.put("contentPartnerName", contentPartnerName);
            resourceMap.put("lastHarvestStatus", lastHarvestStatus);

            resources.add(resourceMap);

        }

        return resources;

    }

    public ArrayList<HashMap<String, String>> getHarvestHistory(Long resourceID) {

        List<Harvest> harvest = harvestRepository.findByResourceId(resourceID);
        ArrayList<HashMap<String, String>> harvestHistory = new ArrayList<>();

        for (Harvest harv : harvest) {

            HashMap<String, String> harvestMap = new HashMap<>();
            harvestMap.put("harvest_id", String.valueOf(harv.getId()));
            harvestMap.put("startTime", String.valueOf(harv.getStart_at()));
            harvestMap.put("endTime", String.valueOf(harv.getCompleted_at()));
            harvestMap.put("status", String.valueOf(harv.getState()));
            harvestMap.put("resourceName", harv.getResource().getName());
            harvestMap.put("contentPartnerID", String.valueOf(harv.getResource().getContentPartner().getId()));

            harvestHistory.add(harvestMap);
        }

        return harvestHistory;
    }

    public HashMap<String, String> getLastHarvest(Long resourceID){
        ArrayList<HashMap<String, String>> harvestHistory = getHarvestHistory(resourceID);
        return harvestHistory.get(harvestHistory.size()-1);
    }

}
