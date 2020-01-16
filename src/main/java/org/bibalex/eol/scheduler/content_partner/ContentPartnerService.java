package org.bibalex.eol.scheduler.content_partner;

import com.sun.org.apache.regexp.internal.RE;
import org.bibalex.eol.scheduler.content_partner.models.LightContentPartner;
import org.bibalex.eol.scheduler.exceptions.NotFoundException;
import org.bibalex.eol.scheduler.resource.Resource;
import org.bibalex.eol.scheduler.resource.ResourceRepository;
import org.bibalex.eol.scheduler.resource.models.LightResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class ContentPartnerService {
    private static final Logger logger = LoggerFactory.getLogger(ContentPartnerService.class);
    @Autowired
    private ContentPartnerRepository contentPartnerRepository;
    @Autowired
    ResourceRepository resourceRepository;

    public long createContentPartner(ContentPartner partner, String logoPath) throws SQLException {
        logger.info("Calling");
        partner.setLogoPath(logoPath);
        partner.setLogoType(getFileExtension(logoPath));
        return contentPartnerRepository.save(partner).getId();
    }

    public long createContentPartner(ContentPartner partner) {
        logger.info("Calling");
        return contentPartnerRepository.save(partner).getId();
    }

    public ContentPartner updateContentPartner(long id, ContentPartner partner) {
        logger.info("Content Partner ID: " + id);
        validateContentPartner(id);
        partner.setId(id);
        return contentPartnerRepository.save(partner);
    }

    public ContentPartner updateContentPartner(long id, ContentPartner partner, String logoPath) throws SQLException {
        logger.info("Content Partner ID: " + id);
        validateContentPartner(id);
        partner.setLogoPath(logoPath);
        partner.setLogoType
                (getFileExtension(logoPath));
        partner.setId(id);
        return contentPartnerRepository.save(partner);
    }

    public List<ContentPartner> getAllContentPartners() {
        List<ContentPartner> partners = new ArrayList<>();
        contentPartnerRepository.findAll().forEach(partners::add);
        return partners;
    }

    public Collection<LightContentPartner> getContentPartners(String partnerIds) {
        logger.info("Content Partner IDs: " + partnerIds);
        if (partnerIds == null || partnerIds != null && partnerIds.length() == 0)
            throw new NotFoundException("content partner", partnerIds);
        return contentPartnerRepository.findByIdIn(Arrays.asList(partnerIds.split("\\s*,\\s*")).stream().map(Long::valueOf).collect(Collectors.toList())).orElseThrow(
                () -> new NotFoundException("content partner", partnerIds));
    }

    public LightContentPartner getContentPartner(long id) {
        return contentPartnerRepository.findById(id).orElseThrow(
                () -> new NotFoundException("content partner", id));
    }

    public void validateContentPartner(long partnerId) {
        contentPartnerRepository.findById(partnerId).orElseThrow(
                () -> new NotFoundException("content partner", partnerId));
    }

    public LightContentPartner getResourceCP(long resId) {
        Resource resource = new Resource();
        resource.setId(resId);
        List<Resource> list = new ArrayList<Resource>();
        list.add(resource);
        return contentPartnerRepository.findByResources(list).orElseThrow(
                () -> new NotFoundException("content partner", resId));
    }

//    public ContentPartner getFullContentPartner(long id) {
//        return contentPartnerRepository.findFullContentPartnerById(id).orElseThrow(
//                () -> new NotFoundException("content partner", id));
//    }

    String getFileExtension(String filePath) {
        int index = filePath.lastIndexOf(File.separator);
        String fileName;
        if (index > 0) {
            fileName = filePath.substring(index + 1);
        } else
            fileName = filePath;
        int dotIndex = fileName.lastIndexOf(".");
        if (fileName.trim().length() > 0 && dotIndex != -1 && dotIndex != 0) {
            return fileName.substring(dotIndex + 1);
        }
        return "";
    }

    public ArrayList<HashMap<String, String>> getAllCPsWithFullData(int offset, int limit) {

        ArrayList<HashMap<String, String>> contentPartners = new ArrayList<>();
        Pageable pageable = new PageRequest(offset, limit);
        Page<ContentPartner> contentPartnersPaged = contentPartnerRepository.findAll(pageable);

        for (ContentPartner contentPartner : contentPartnersPaged) {
            HashMap<String, String> contentPartnersMap = new HashMap();
            contentPartnersMap.put("contentPartnerID", String.valueOf(contentPartner.getId()));
            contentPartnersMap.put("contentPartnerName", contentPartner.getName());
            contentPartnersMap.put("logoType", contentPartner.getLogoType());
            contentPartnersMap.put("logoPath", contentPartner.getLogoPath());
            contentPartners.add(contentPartnersMap);
//            System.out.println(contentPartner.getLogoPath());
        }
        return contentPartners;
    }

    public Long getContentPartnerCount() {
        return contentPartnerRepository.count();
    }

    public HashMap<String, Object> getFullContentPartner(long id) {

        LightContentPartner contentPartner = contentPartnerRepository.findContentPartnerById(id);
        HashMap<String, Object> fullContentPartner = new HashMap<>();
        fullContentPartner.put("id", contentPartner.getId());
        fullContentPartner.put("name", contentPartner.getName());
        fullContentPartner.put("abbreviation", contentPartner.getAbbreviation());
        fullContentPartner.put("url", contentPartner.getUrl());
        fullContentPartner.put("description", contentPartner.getDescription());
        fullContentPartner.put("logoType", contentPartner.getLogoType());
        fullContentPartner.put("logoPath", contentPartner.getLogoPath());

        List<LightResource> cpResources = new ArrayList(),
                resources;

        int limit = 20,
                offset = 0;
        do {
            Pageable pageable = new PageRequest(offset, limit);
            resources = resourceRepository.findByContentPartnerId(id, pageable);
            cpResources.addAll(resources);
            offset += limit;
        }
        while (!(resources.isEmpty()));

        fullContentPartner.put("resources", cpResources);

        return fullContentPartner;
    }

    public HashMap<String, Object> getContentPartnerWithoutResources(long id) {

        LightContentPartner lightContentPartner = contentPartnerRepository.findContentPartnerById(id);
        HashMap<String, Object> contentPartner = new HashMap<>();
        contentPartner.put("id", lightContentPartner.getId());
        contentPartner.put("name", lightContentPartner.getName());
//        contentPartner.put("abbreviation", lightContentPartner.getAbbreviation());
//        contentPartner.put("url", lightContentPartner.getUrl());
//        contentPartner.put("description", lightContentPartner.getDescription());
        contentPartner.put("logoType", lightContentPartner.getLogoType());
        contentPartner.put("logoPath", lightContentPartner.getLogoPath());

        return contentPartner;
    }
}
