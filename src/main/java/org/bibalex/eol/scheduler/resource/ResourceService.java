package org.bibalex.eol.scheduler.resource;

import org.apache.log4j.Logger;
import org.bibalex.eol.scheduler.content_partner.ContentPartner;
import org.bibalex.eol.scheduler.content_partner.ContentPartnerService;
import org.bibalex.eol.scheduler.exceptions.NotFoundException;
import org.bibalex.eol.scheduler.harvest.HarvestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;


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
        return resourceRepository.save(resource).getId();
    }

    public Resource updateResource(long contentPartnerId, Long resourceId, Resource resource){
        contentPartnerService.validateContentPartner(contentPartnerId);
        validateResource(resourceId);
        resource.setContentPartner(new ContentPartner(contentPartnerId));
        resource.setId(resourceId);
         return resourceRepository.save(resource);
    }

    public Resource getResource(long resourceId){
        return resourceRepository.findById(resourceId).orElseThrow(() -> new NotFoundException("resource", resourceId));
    }

    public List<Resource> getResources(long contentPartnerId){
        contentPartnerService.validateContentPartner(contentPartnerId);
        return resourceRepository.findByContentPartnerId(contentPartnerId);
    }
    public void validateResource(long resourceId){
        resourceRepository.findById(resourceId).orElseThrow(() -> new NotFoundException("resource", resourceId));
    }

    public boolean checkReadyResources(Date dt) {
        StoredProcedureQuery getResourcesQuery =
                entityManager.createNamedStoredProcedureQuery("getHarvestedResources_sp");

        StoredProcedureQuery storedProcedure =
                getResourcesQuery.setParameter("cDate", dt);

        BigInteger count = (BigInteger) storedProcedure.getSingleResult();
        logger.debug("checkReadyResources count:"  + count.toString());
        System.out.println("checkReadyResources" + count);
        return (count.signum() == 1? true : false);
    }
}
