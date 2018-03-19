package org.bibalex.eol.scheduler.harvest;

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

public class HarvesterClient {
    public void callHarvester(String resId){
        System.out.println("start calling  harvester");

        Properties prop = new Properties();
        InputStream input = HarvesterClient.class.getClassLoader().getResourceAsStream("application.properties");
        try {
            prop.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
//
//        Properties properties = new Properties();
//        try (InputStream is = getClass().getResourceAsStream("application.properties")) {
//            System.out.println("start loading prop");
//            properties.load(is);
//        } catch (IOException e) {
//            System.out.println("fail loading prop");
//            e.printStackTrace();
//        }
//        System.out.println("done loading prop");


//        String uri = "http://localhost:8090/harvest/";
        String uri = prop.getProperty("harvester");
        System.out.println(uri);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(
                new ByteArrayHttpMessageConverter());

        System.out.println("done rest template");
        Map<String, String> params = new HashMap<String, String>();
        params.put(prop.getProperty("resourceId"), resId);


        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add(prop.getProperty("resourceId"),resId);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Accept", "text/plain"); // looks like you want a string back


        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity =
                new HttpEntity<LinkedMultiValueMap<String, Object>>(map, headers);

        System.out.println("before send request");
        ResponseEntity<String> response = restTemplate.exchange(
                uri,
                HttpMethod.POST, requestEntity, String.class, params);


        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("done");
//            logger.debug("Uplaoded DWCA resource (" + resId + ")");
        } else {
            System.out.println("fail");
//            logger.error("org.bibalex.eol.harvester.client.StorageLayerClient.uploadDWCAResource: returned code(" + response.getStatusCode() + ")");
        }


    }
}
