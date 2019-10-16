package org.bibalex.eol.scheduler.harvest;

import org.bibalex.eol.scheduler.resource.ResourceController;
import org.bibalex.eol.scheduler.resource.models.LightResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

@RestController
@RequestMapping("/")
public class HarvestController {
    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);
    @Autowired
    private HarvestService harvestService;

    @RequestMapping(value="setMediaStatus/{id}/{mediaStatus}", method = RequestMethod.GET)
    public boolean setMediaStatus(@PathVariable("id") int id,@PathVariable("mediaStatus") int mediaStatus) {
        logger.info("set media status for Resource ID: " + id + "status: " + mediaStatus);
        return harvestService.setMediaStatus(id, mediaStatus);
    }
}
