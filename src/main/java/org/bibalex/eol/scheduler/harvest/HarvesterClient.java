package org.bibalex.eol.scheduler.harvest;

//import org.apache.http.HttpHost;
//import org.apache.http.auth.AuthScope;
//import org.apache.http.auth.UsernamePasswordCredentials;
//import org.apache.http.client.CredentialsProvider;
//import org.apache.http.impl.client.BasicCredentialsProvider;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.apache.log4j.Logger;
//import org.bibalex.eol.scheduler.utils.PropertiesFile;
//import org.springframework.http.*;
//import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
/**
 * Created by hduser on 1/31/18.
 */
public class HarvesterClient {

    private static final Logger logger = Logger.getLogger(HarvesterClient.class);
//    private PropertiesFile app;

//    public void setApp(PropertiesFile app) {
//        this.app = app;
//    }

    public Harvest.State harvestResource(String resId) {
        try {
            logger.debug("\nDownloading resource into harvester: " + resId);
            System.out.println("\nDownloading resource into harvester: " + resId);

            Properties prop = new Properties();
            InputStream input = HarvesterClient.class.getClassLoader().getResourceAsStream("application.properties");
            try {
                prop.load(input);

            } catch (IOException e) {
                System.out.println("org.bibalex.eol.scheduler.harvest.HarvesterClient.harvestResource: Error during loading properties files.");
                logger.error("\norg.bibalex.eol.scheduler.harvest.HarvesterClient.harvestResource: Error during loading properties files.");
                return Harvest.State.failed;
            }
            String uri = prop.getProperty("harvester");
            logger.debug("\ncreating msg converter");
            System.out.println("\ncreating msg converter");
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(
                    new ByteArrayHttpMessageConverter());

            logger.debug("\ncreating params");
            System.out.println("\ncreating params");

            Map<String, String> params = new HashMap<String, String>();
            params.put(prop.getProperty("resourceId"), resId);

            LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.add(prop.getProperty("resourceId"),resId);

            HttpHeaders headers = new HttpHeaders();
            HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity =
                    new HttpEntity<LinkedMultiValueMap<String, Object>>(map, headers);

            logger.debug("Sending harvester request.");
            ResponseEntity<Boolean> response = restTemplate.exchange(
                    uri,
                    HttpMethod.POST, requestEntity, Boolean.class, params);

            logger.debug("after exchange now ..");
            System.out.println("after exchange now ..");
            if (response.getStatusCode() == HttpStatus.OK) {
                logger.debug("\nharvested resource (" + resId + ") successfully.");
                return Harvest.getHarvestStatus(String.valueOf((Boolean)(response.getBody())));
            } else {
                System.out.println("\norg.bibalex.eol.scheduler.harvest.HarvesterClient.harvestResource: returned code(" + response.getStatusCode() + ")");
                logger.error("\norg.bibalex.eol.scheduler.harvest.HarvesterClient.harvestResource: returned code(" + response.getStatusCode() + ")");
                return Harvest.State.failed;
            }
        } catch(Exception ex) {
            logger.error("\norg.bibalex.eol.scheduler.harvest.HarvesterClient.harvestResource: Error during harvesting");
            System.out.println("\norg.bibalex.eol.scheduler.harvest.HarvesterClient.harvestResource: Error during harvesting");
            return Harvest.State.failed;

        }
    }


}
