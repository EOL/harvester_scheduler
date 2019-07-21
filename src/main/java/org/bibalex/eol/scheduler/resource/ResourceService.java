package org.bibalex.eol.scheduler.resource;

import org.apache.log4j.Logger;
import org.bibalex.eol.scheduler.content_partner.ContentPartner;
import org.bibalex.eol.scheduler.content_partner.ContentPartnerService;
import org.bibalex.eol.scheduler.exceptions.NotFoundException;
import org.bibalex.eol.scheduler.resource.models.LightResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ResourceService {

    private static final Logger logger = Logger.getLogger(ResourceService.class);
    @Autowired
    private ResourceRepository resourceRepository;
    @Autowired
    private ContentPartnerService contentPartnerService;
    @PersistenceContext
    private EntityManager entityManager;

    public long createResource(long contentPartnerId, Resource resource){
        contentPartnerService.validateContentPartner(contentPartnerId);
        resource.setContentPartner(new ContentPartner(contentPartnerId));

        // reharvest
//        if(resourceRepository.findById(resource.getId()).isPresent()) {
//            resource.setForced_internally(true);
//        }
        return resourceRepository.save(resource).getId();
    }

    public Resource updateResource(long contentPartnerId, Long resourceId, Resource resource){
        contentPartnerService.validateContentPartner(contentPartnerId);
        validateResource(resourceId);
        resource.setContentPartner(new ContentPartner(contentPartnerId));
        resource.setId(resourceId);
         return resourceRepository.save(resource);
    }

    public LightResource getResource(long resourceId){
        return resourceRepository.findById(resourceId).orElseThrow(() -> new NotFoundException("resource", resourceId));
    }

//    public Resource getCompleteResource(long resourceId){
//        return resourceRepository.findAllById(resourceId).orElseThrow(() -> new NotFoundException("resource", resourceId));
//    }

    public List<LightResource> getResources(long contentPartnerId){
        contentPartnerService.validateContentPartner(contentPartnerId);
        return resourceRepository.findByContentPartnerId(contentPartnerId);
    }
    public void validateResource(long resourceId){
        resourceRepository.findById(resourceId).orElseThrow(() -> new NotFoundException("resource", resourceId));
    }

    public boolean checkReadyResources(Timestamp ts) {
        StoredProcedureQuery getResourcesQuery =
                entityManager.createNamedStoredProcedureQuery("getHarvestedResources_sp");

        StoredProcedureQuery storedProcedure =
                getResourcesQuery.setParameter("cDate", ts);

        BigInteger count = (BigInteger) storedProcedure.getSingleResult();
        logger.debug("Number of Ready Resources: "  + count.toString());
//        System.out.println("checkReadyResources: " + count);
        return (count.signum() == 1? true : false);
    }

    public Collection<LightResource> getResources(String resourcesIds){
        logger.debug("Resources service: get resources with ids : " + resourcesIds);
        if (resourcesIds == null || resourcesIds != null && resourcesIds.length() == 0){
            logger.error("Exception: Resources not Found");
            throw new NotFoundException("resources ", resourcesIds);}
        return resourceRepository.findByIdIn(Arrays.asList(resourcesIds.split("\\s*,\\s*")).stream().map(Long::valueOf).collect(Collectors.toList())).orElseThrow(
                () -> new NotFoundException("resources", resourcesIds));
    }

    public LightResource getLightResource(long id){
        return resourceRepository.findById(id).orElseThrow(() -> new NotFoundException("resource", 1));
    }

    public Long getResourceCount() {
        return resourceRepository.count();
    }

//    public Resource getResourceWithCP(long resourceId){
//        return resourceRepository.findByIdWithCP(resourceId).orElseThrow(() -> new NotFoundException("resource", resourceId));
//    }
}
