package org.bibalex.eol.scheduler.content_partner;

import org.bibalex.eol.scheduler.content_partner.models.LightContentPartner;
import org.bibalex.eol.scheduler.resource.models.LightResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.Callable;


@RestController
@RequestMapping("/contentPartners")
public class ContentPartnerController {
    private static final Logger logger = LoggerFactory.getLogger(ContentPartnerController.class);
    @Autowired
    private ContentPartnerService contentPartnerService;

    @RequestMapping(method = RequestMethod.POST, params = "partner")
    @ResponseBody
    public Callable<ResponseEntity<?>> createContentPartner(@RequestPart ContentPartner partner) throws IOException, SQLException {
        logger.info("Request: "+ partner.toString());
        ResponseEntity responseEntity = ResponseEntity.ok(contentPartnerService.createContentPartner(partner));
        logger.info("Response: " + responseEntity);
        return () -> responseEntity;
    }


    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Callable<ResponseEntity<?>> createContentPartner(@RequestPart(required = false) String logoPath, @RequestPart String name, @RequestPart(required = false) String abbreviation,
                                                            @RequestPart String description, @RequestPart(required = false) String url)
            throws IOException, SQLException {
        ContentPartner partner = new ContentPartner();
        partner.setName(name);
        partner.setAbbreviation(abbreviation);
        partner.setDescription(description);
        partner.setUrl(url);
        partner.setLogoPath(logoPath);
        partner.setLogoType(contentPartnerService.getFileExtension(logoPath));
        logger.info("Content Partner: "+ partner.toString());
        ResponseEntity responseEntity = ResponseEntity.ok(contentPartnerService.createContentPartner(partner));
        logger.info("Response: " + responseEntity);
        return () -> responseEntity;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST, params = "partner")
    public Callable<ResponseEntity<?>> updateContentPartner(@PathVariable long id, @RequestPart(required = false) String logoPath, @RequestPart ContentPartner partner) throws IOException, SQLException {
        logger.info("Content Partner ID: "+ id);
        logger.info("Updated Attributes: " + partner.toString());
        if (logoPath != null) {
            ResponseEntity responseEntity = ResponseEntity.ok(contentPartnerService.updateContentPartner(id, partner, logoPath));
            logger.info("Response: " + responseEntity);
            return () -> responseEntity;
        } else{
            ResponseEntity responseEntity = ResponseEntity.ok(contentPartnerService.updateContentPartner(id, partner));
            logger.info("Response: " + responseEntity);
            return () -> responseEntity;
    }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Callable<ResponseEntity<?>> updateContentPartner(@PathVariable long id, @RequestPart(required = false) String logoPath, @RequestPart String name, @RequestPart(required = false) String abbreviation,
                                                            @RequestPart String description, @RequestPart(required = false) String url) throws IOException, SQLException {
        ContentPartner partner = new ContentPartner();
        partner.setName(name);
        partner.setAbbreviation(abbreviation);
        partner.setDescription(description);
        partner.setUrl(url);
        logger.info("Content Partner ID: "+ id);
        logger.info("Updated Attributes: " + partner.toString());

        if (logoPath != null) {
            ResponseEntity responseEntity = ResponseEntity.ok(contentPartnerService.updateContentPartner(id, partner, logoPath));
            logger.info("Response: " + responseEntity);
            return () -> responseEntity;
        } else{
            ResponseEntity responseEntity = ResponseEntity.ok(contentPartnerService.updateContentPartner(id, partner));
            logger.info("Response: " + responseEntity);
            return () -> responseEntity;
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public Callable<ResponseEntity<Collection<LightContentPartner>>> getContentPartners(@RequestParam String ids){
        logger.info("Content Partners IDs: "+ids);
        ResponseEntity responseEntity = ResponseEntity.ok(contentPartnerService.getContentPartners(ids));
        logger.info("Response: " + responseEntity);
        return () -> responseEntity;
    }

    @RequestMapping(value= "/{id}", method = RequestMethod.GET)
    public ResponseEntity<LightContentPartner> getContentPartner(@PathVariable long id) {
        logger.info("Content Partner ID: " + id);
        ResponseEntity responseEntity = ResponseEntity.ok(contentPartnerService.getContentPartner(id));
        logger.info("Response: " + responseEntity);
        return responseEntity;
    }

    @RequestMapping(method = RequestMethod.GET, value = "getAllCPsWithFullData/{offset}/{limit}", produces = "application/json")
    public ResponseEntity<ArrayList<HashMap<String, String>>> getAllCPsWithFullData(@PathVariable("offset") int offset,
                                                                                    @PathVariable("limit") int limit) {
        ResponseEntity responseEntity = ResponseEntity.ok(contentPartnerService.getAllCPsWithFullData(offset, limit));
        return responseEntity;
    }


    @RequestMapping(method = RequestMethod.POST, value = "contentPartnerOfResource")
    public Callable<ResponseEntity<LightContentPartner>> getContentPartnerOfResource(@RequestParam("resId") long resId){
        logger.info("Resource ID: " + resId);
        ResponseEntity responseEntity = ResponseEntity.ok(contentPartnerService.getResourceCP(resId));
        logger.info("Response: " + responseEntity);
        return () ->  responseEntity;
    }

    @RequestMapping(method = RequestMethod.GET, value = "contentPartnerWithResources/{id}")
    public Callable<ResponseEntity<ContentPartner>> getFullContentPartner(@PathVariable long id){
        logger.info("Content Partner ID: " + id);
        ResponseEntity responseEntity = ResponseEntity.ok(contentPartnerService.getFullContentPartner(id));
        logger.info("Response: " + responseEntity);
        return () -> responseEntity;
    }

    @RequestMapping(method = RequestMethod.GET, value = "count")
    public Callable<ResponseEntity<Long>> getContentPartnerCount() {
        ResponseEntity responseEntity = ResponseEntity.ok(contentPartnerService.getContentPartnerCount());
        logger.info("Response: " + responseEntity);
        return () -> responseEntity;
    }

    @RequestMapping(method = RequestMethod.GET, value = "getContentPartnerWithoutResources/{id}")
    public Callable<ResponseEntity<Long>> getContentPartnerWithoutResources(@PathVariable long id) {
        ResponseEntity responseEntity = ResponseEntity.ok(contentPartnerService.getContentPartnerWithoutResources(id));
        logger.info("Response: " + responseEntity);
        return () -> responseEntity;
    }
}

