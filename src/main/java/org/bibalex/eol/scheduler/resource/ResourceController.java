package org.bibalex.eol.scheduler.resource;

import org.apache.log4j.Logger;
import org.bibalex.eol.scheduler.content_partner.ContentPartner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;



@RestController
@RequestMapping("/")
public class ResourceController {
    private static final Logger logger = Logger.getLogger(ResourceController.class);
    @Autowired
    private ResourceService resourceService;

    @RequestMapping(method = RequestMethod.POST, value = "{contentPartnerId}/resources")
    public Callable<ResponseEntity<?>> createResource(@PathVariable long contentPartnerId, @RequestBody Resource resource){
        logger.debug("Create new resource with partner id "+contentPartnerId);
        return () -> ResponseEntity.ok(resourceService.createResource(contentPartnerId, resource));
    }
    @RequestMapping(method = RequestMethod.POST, value = "{contentPartnerId}/resources/{resourceId}")
    public Callable<ResponseEntity<Resource>> updateResource(@PathVariable long contentPartnerId, @PathVariable long resourceId, @RequestBody Resource resource){
        logger.debug("Update resource with id "+resourceId );
        return () ->  ResponseEntity.ok(resourceService.updateResource(contentPartnerId, resourceId, resource));
    }

    @RequestMapping(method = RequestMethod.GET, value = "{contentPartnerId}/resources/{resourceId}")
    public Callable<ResponseEntity<Resource>> getResource(@PathVariable long resourceId){
        logger.debug("Get resource of id "+resourceId );
        return () ->  ResponseEntity.ok(resourceService.getResource(resourceId));
    }

    @RequestMapping(method = RequestMethod.GET, value = "{contentPartnerId}/resources")
    public ResponseEntity<Collection<Resource>> getResources(@PathVariable long contentPartnerId){
        List<Resource> resources = resourceService.getResources(contentPartnerId);
        HttpStatus status = HttpStatus.OK;
        if (resources == null)
            status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(resources, status);
    }

    @RequestMapping(method = RequestMethod.GET, value = "resources/{resourceId}")
    public Callable<ResponseEntity<Resource>> getResourceWithoutCP(@PathVariable long resourceId){
        logger.debug("Get resource of id "+resourceId );
        return () ->  ResponseEntity.ok(resourceService.getResource(resourceId));
    }

    @RequestMapping(method = RequestMethod.GET, value = "resources/{resourceId}/contentPartner")
    public Callable<ResponseEntity<ContentPartner>> getResourceCP(@PathVariable long resourceId){
        logger.debug("Get resource of id "+resourceId );
        return () ->  ResponseEntity.ok(resourceService.getResource(resourceId).getContentPartner());
    }

    @RequestMapping(method = RequestMethod.POST, value = "resources")
    public Callable<ResponseEntity<Boolean>> getResourceWithoutCP(@RequestParam("fromDate") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date date){
        logger.debug("Get ready harvested resources.");
        return () ->  ResponseEntity.ok(resourceService.checkReadyResources(date));
    }
}

