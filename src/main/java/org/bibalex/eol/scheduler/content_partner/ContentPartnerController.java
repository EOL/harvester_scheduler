package org.bibalex.eol.scheduler.content_partner;

import org.apache.log4j.Logger;
import org.bibalex.eol.scheduler.content_partner.models.LightContentPartner;
import org.bibalex.eol.scheduler.resource.models.LightResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;


@RestController
@RequestMapping("/contentPartners")
public class ContentPartnerController {
    private static final Logger logger = Logger.getLogger(ContentPartnerController.class);
    @Autowired
    private ContentPartnerService contentPartnerService;

    @RequestMapping( method = RequestMethod.POST, params = "partner")
    @ResponseBody
    public Callable<ResponseEntity<?>> createContentPartner(@RequestPart ContentPartner partner) throws IOException, SQLException {
        logger.debug("Create content partner request ->"+ partner.toString());

        return () -> ResponseEntity.ok(contentPartnerService.createContentPartner(partner));
    }


    @RequestMapping( method = RequestMethod.POST)
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
        logger.debug("Create content partner request -> "+ partner.toString());
        return () -> ResponseEntity.ok(contentPartnerService.createContentPartner(partner));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST, params = "partner")
    public Callable<ResponseEntity<?>> updateContentPartner(@PathVariable long id, @RequestPart(required = false) String logoPath, @RequestPart ContentPartner partner) throws IOException, SQLException {
        logger.debug("Update content partner id: "+id +" with vals ->"+ partner.toString());
        if (logoPath != null) {
            return () -> ResponseEntity.ok(contentPartnerService.updateContentPartner(id, partner, logoPath));
        } else
            return () -> ResponseEntity.ok(contentPartnerService.updateContentPartner(id, partner));
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
        logger.debug("Update content partner id: "+id +" with vals ->"+ partner.toString());
        if (logoPath != null) {
            return () -> ResponseEntity.ok(contentPartnerService.updateContentPartner(id, partner, logoPath));
        } else
            return () -> ResponseEntity.ok(contentPartnerService.updateContentPartner(id, partner));
    }

    @RequestMapping(method = RequestMethod.GET)
    public Callable<ResponseEntity<Collection<LightContentPartner>>> getContentPartners(@RequestParam String ids){
        logger.debug("Get content partners with ids: "+ids);
        return () -> ResponseEntity.ok(contentPartnerService.getContentPartners(ids));
    }

    @RequestMapping(value= "/{id}", method = RequestMethod.GET)
    public ResponseEntity<LightContentPartner> getContentPartner(@PathVariable long id){
        logger.debug("Content partner controller get partner with id: "+id);
        return ResponseEntity.ok(contentPartnerService.getContentPartner(id));
    }

    @RequestMapping(method = RequestMethod.POST, value = "contentPartnerOfResource")
    public Callable<ResponseEntity<LightContentPartner>> getContentPartnerOfResource(@RequestParam("resId") long resId){

        logger.debug("Get content partner of resource (" + resId + ")");
        return () ->  ResponseEntity.ok(contentPartnerService.getResourceCP(resId));
    }

    @RequestMapping(method = RequestMethod.GET, value = "contentPartnerWithResources/{id}")
    public Callable<ResponseEntity<ContentPartner>> getFullContentPartner(@PathVariable long id){
        logger.debug("Get content partners resources with ids: " + id);
        return () -> ResponseEntity.ok(contentPartnerService.getFullContentPartner(id));
    }

}
