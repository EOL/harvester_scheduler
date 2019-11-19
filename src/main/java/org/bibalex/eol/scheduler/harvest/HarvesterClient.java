package org.bibalex.eol.scheduler.harvest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
/**
 * Created by hduser on 1/31/18.
 */
public class HarvesterClient {

    private static final Logger logger = LoggerFactory.getLogger(HarvesterClient.class);
//    private PropertiesFile app;

//    public void setApp(PropertiesFile app) {
//        this.app = app;
//    }

    public Harvest.State harvestResource(String resId) {
        try {
            logger.info("Downloading Resource: " + resId +" into Harvester");
//            System.out.println("\nDownloading resource into harvester: " + resId);

            Properties prop = new Properties();
            InputStream input = HarvesterClient.class.getClassLoader().getResourceAsStream("application.properties");
            try {
                prop.load(input);

            } catch (IOException e) {
//                System.out.println("org.bibalex.eol.scheduler.harvest.HarvesterClient.harvestResource: Error during loading properties files.");
                logger.error("IOException: Error during loading properties files.");
                logger.error("Stack Trace: ", e);
                return Harvest.State.failed;
            }
            String uri = prop.getProperty("harvester");
            logger.debug("Creating Message Converter");
//            System.out.println("\ncreating msg converter");
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(
                    new ByteArrayHttpMessageConverter());

            logger.debug("Creating Parameters");
//            System.out.println("\ncreating params");

            Map<String, String> params = new HashMap<String, String>();
            params.put(prop.getProperty("resourceId"), resId);

            LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add(prop.getProperty("resourceId"),resId);

            HttpHeaders headers = new HttpHeaders();
            HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity =
                    new HttpEntity<LinkedMultiValueMap<String, Object>>(map, headers);

            logger.debug("Sending Harvester Request");
            ResponseEntity<Boolean> response = restTemplate.exchange(
                    uri,
                    HttpMethod.POST, requestEntity, Boolean.class, params);

            logger.debug("Getting HTTP Response");
//            System.out.println("after exchange now ..");
            if (response.getStatusCode() == HttpStatus.OK) {
                logger.debug("Resource: " + resId + "- Harvested Successfully");
                return Harvest.getHarvestStatus(String.valueOf((Boolean)(response.getBody())));
            } else {
                logger.error("Failed to Harvest Resource: " + resId);
                logger.error("Status Code: " + response.getStatusCode());
                return Harvest.State.failed;
            }
        } catch(Exception ex) {
            logger.error("Exception: Error during Harvesting");
            logger.error("Stack Trace: ", ex);
//            System.out.println("\norg.bibalex.eol.scheduler.harvest.HarvesterClient.harvestResource: Error during harvesting");
            return Harvest.State.failed;

        }
    }


}
