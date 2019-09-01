package org.bibalex.eol.scheduler.resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bibalex.eol.scheduler.resource.models.LightResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;


@RestController
@RequestMapping("/")
public class ResourceController {
    private static final Logger logger = LogManager.getLogger(ResourceController.class);

    @Autowired
    private ResourceService resourceService;

    @RequestMapping(method = RequestMethod.POST, value = "{contentPartnerId}/resources")
    public Callable<ResponseEntity<?>> createResource(@PathVariable long contentPartnerId, @RequestBody Resource resource) {
        logger.info("Content Partner ID: " + contentPartnerId);
        ResponseEntity responseEntity = ResponseEntity.ok(resourceService.createResource(contentPartnerId, resource));
        logger.info("Response: " + responseEntity);
        return () -> responseEntity;
    }

    @RequestMapping(method = RequestMethod.POST, value = "{contentPartnerId}/resources/{resourceId}")
    public Callable<ResponseEntity<Resource>> updateResource(@PathVariable long contentPartnerId, @PathVariable long resourceId, @RequestBody Resource resource) {
        logger.info("Resource ID: " + resourceId);
        ResponseEntity responseEntity = ResponseEntity.ok(resourceService.updateResource(contentPartnerId, resourceId, resource));
        logger.info("Response: " + responseEntity);
        return () -> responseEntity;
    }

    @RequestMapping(method = RequestMethod.GET, value = "{contentPartnerId}/resources/{resourceId}")
    public Callable<ResponseEntity<LightResource>> getResource(@PathVariable long resourceId) {
        logger.info("Resource ID: " + resourceId);
        ResponseEntity responseEntity = ResponseEntity.ok(resourceService.getResource(resourceId));
        logger.info("Response: " + responseEntity);
        return () -> responseEntity;
    }

    @RequestMapping(method = RequestMethod.GET, value = "{contentPartnerId}/resources")
    public ResponseEntity<Collection<LightResource>> getResources(@PathVariable long contentPartnerId) {
        List<LightResource> resources = resourceService.getResources(contentPartnerId);
        logger.info("Content Partner ID: " + contentPartnerId);
        HttpStatus status = HttpStatus.OK;
        if (resources == null)
            status = HttpStatus.NOT_FOUND;
        logger.info("HTTP Status: " + status);
        return new ResponseEntity<>(resources, status);
    }

    @RequestMapping(method = RequestMethod.GET, value = "resources/{resourceId}")
    public Callable<ResponseEntity<LightResource>> getResourceWithoutCP(@PathVariable long resourceId) {
        logger.info("Resource ID: " + resourceId);
        ResponseEntity responseEntity = ResponseEntity.ok(resourceService.getResource(resourceId));
        logger.info("Response: " + responseEntity);
        return () -> responseEntity;
    }

//    @RequestMapping(method = RequestMethod.POST, value = "resources")
//    public Callable<ResponseEntity<Boolean>> checkReadyResources(@RequestParam("fromDate") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date date){
//        logger.debug("Get ready harvested resources.");
//        return () ->  ResponseEntity.ok(resourceService.checkReadyResources(date));
//    }

    @RequestMapping(method = RequestMethod.GET, value = "readyResources/{fromDate}")
    public Callable<ResponseEntity<Boolean>> checkReadyResources(@PathVariable("fromDate") String tsStr) {
        Timestamp ts = new java.sql.Timestamp(Long.parseLong(tsStr));
        logger.info("Timestamp: " + ts);
        ResponseEntity responseEntity = ResponseEntity.ok(resourceService.checkReadyResources(ts));
//        System.out.println("-------->"+ts);
        logger.info("Response: " + responseEntity);
        return () -> responseEntity;
    }

    @RequestMapping(method = RequestMethod.GET, value = "resources")
    public Callable<ResponseEntity<Collection<LightResource>>> getResources(@RequestParam("ids") String ids) {
        logger.info("Resource IDs: " + ids);
        return () -> ResponseEntity.ok(resourceService.getResources(ids));
    }

    //    @RequestMapping(method = RequestMethod.GET, value = "resources/{resourceId}/contentPartner")
//    public Callable<ResponseEntity<Resource>> getResourceWithCP(@PathVariable long resourceId){
//        logger.debug("Get resource of id "+resourceId );
//        return () ->  ResponseEntity.ok(resourceService.getResourceWithCP(resourceId));
//    }
    @RequestMapping(method = RequestMethod.GET, value = "resources/count")
    public Callable<ResponseEntity<Long>> getResourceCount() {
        ResponseEntity responseEntity = ResponseEntity.ok(resourceService.getResourceCount());
        logger.info("Resource Count: " + responseEntity);
//    System.out.println("Get Resource Count");
        return () -> responseEntity;
    }

    @RequestMapping(method = RequestMethod.GET, value = "getHarvestHistory/{resourceID}", produces = "application/json")
    public ResponseEntity<ArrayList<HashMap<String, String>>> getHarvestHistory(@PathVariable("resourceID") Long resourceID) {
        ResponseEntity responseEntity = ResponseEntity.ok(resourceService.getHarvestHistory(resourceID));
        return responseEntity;
    }

    @RequestMapping(method = RequestMethod.GET, value = "getAllResourcesWithFullData/{startResourceID}/{endResourceID}", produces = "application/json")
    public ResponseEntity<ArrayList<HashMap<String, String>>> getAllResourcesWithFullData(@PathVariable("startResourceID") Long startResourceID,
                                                                                          @PathVariable("endResourceID") Long endResourceID ) {
        ResponseEntity responseEntity = ResponseEntity.ok(resourceService.getAllResourcesWithFullData(startResourceID, endResourceID));
        return responseEntity;
    }

    @RequestMapping(method = RequestMethod.GET, value = "getResourceBoundaries")
    public ResponseEntity<HashMap<String, Long>> getResourceBoundaries() {
        ResponseEntity responseEntity = ResponseEntity.ok(resourceService.getResourceBoundaries());
        return responseEntity;
    }
}

