package org.bibalex.eol.scheduler.content_partner;

import org.bibalex.eol.scheduler.content_partner.models.LightContentPartner;
import org.bibalex.eol.scheduler.exceptions.NotFoundException;
import org.bibalex.eol.scheduler.resource.Resource;
import org.bibalex.eol.scheduler.resource.models.LightResource;
import org.bibalex.eol.scheduler.utils.OffsetBasedPageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public ContentPartner getFullContentPartner(long id) {
        return contentPartnerRepository.findFullContentPartnerById(id).orElseThrow(
                () -> new NotFoundException("content partner", id));
    }

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

        for (ContentPartner contentPartner: contentPartnersPaged){
            HashMap<String, String> contentPartnersMap = new HashMap();
            contentPartnersMap.put("contentPartnerID", String.valueOf(contentPartner.getId()));
            contentPartnersMap.put("contentPartnerName", contentPartner.getName());
            contentPartners.add(contentPartnersMap);
        }

        return contentPartners;
    }

    public Long getContentPartnerCount() {
        return contentPartnerRepository.count();
    }
}
