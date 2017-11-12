package org.bibalex.eol.scheduler.content_partner;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.concurrent.Callable;

/**
 * Created by sara.mustafa on 4/11/17.
 */

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

//    @RequestMapping(method = RequestMethod.POST, value="/create")
//    public Callable<ResponseEntity<?>> create(@RequestBody  ContentPartner partner){
//        logger.debug("--------------------");
//        if(partner.getResources().size() > 0) {
//            System.out.println("size > 0");
//            partner.getResources().stream().forEach(x -> {System.out.println(x.getName());});
//        }
//        return () -> ResponseEntity.ok(contentPartnerService.createContentPartner(partner));
//    }

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
        partner.setLogo_path(logoPath);
        partner.setLogo_type(contentPartnerService.getFileExtension(logoPath));
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
    public Callable<ResponseEntity<Collection<ContentPartner>>> getContentPartners(@RequestParam String ids){
        logger.debug("Get content partners with ids: "+ids);
        return () -> ResponseEntity.ok(contentPartnerService.getContentPartners(ids));
    }

    @RequestMapping(value= "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ContentPartner> getContentPartner(@PathVariable long id){
        logger.debug("Content partner controller get partner with id: "+id);
        return ResponseEntity.ok(contentPartnerService.getContentPartner(id));
    }

}
