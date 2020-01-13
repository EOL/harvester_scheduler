package org.bibalex.eol.scheduler.harvest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/harvest")

public class HarvesterController {

    @Autowired
    private HarvestService harvestService;

//    @RequestMapping(method = RequestMethod.GET, value = "getAll/{offset}/{limit}")
//    public ResponseEntity<HashMap<String, Long>> getAllHarvests(@PathVariable("offset") int offset,
//                                                                @PathVariable("limit") int limit) {
//        ResponseEntity responseEntity = ResponseEntity.ok(harvestService.getAllHarvests(offset, limit));
//        return responseEntity;
//    }

    @RequestMapping(method = RequestMethod.GET, value = "getHarvests/{state}/{offset}/{limit}")
    public ResponseEntity<HashMap<String, String>> getCurrentHarvesting(@PathVariable("state") int state,
                                                                        @PathVariable("offset") int offset,
                                                                        @PathVariable("limit") int limit) {
        ResponseEntity responseEntity = ResponseEntity.ok(harvestService.getHarvests(state, offset, limit));
        return responseEntity;
    }

}
