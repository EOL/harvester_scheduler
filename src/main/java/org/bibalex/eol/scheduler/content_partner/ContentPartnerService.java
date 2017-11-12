package org.bibalex.eol.scheduler.content_partner;

import org.bibalex.eol.scheduler.exceptions.NotFoundException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sara.mustafa on 4/11/17.
 */

@Service
public class ContentPartnerService {
    private static final Logger logger = Logger.getLogger(ContentPartnerService.class);
    @Autowired
    private ContentPartnerRepository contentPartnerRepository;

    public long createContentPartner(ContentPartner partner, String logoPath) throws SQLException{
            logger.debug("Content partner Service create new ");
            partner.setLogo_path(logoPath);
            partner.setLogo_type(getFileExtension(logoPath));
            return contentPartnerRepository.save(partner).getId();
    }
    public long createContentPartner(ContentPartner partner){
        logger.debug("Content partner Service create new ");
        return contentPartnerRepository.save(partner).getId();
    }

    public ContentPartner updateContentPartner(long id, ContentPartner partner){
        logger.debug("Content partner Service update id "+id);
        validateContentPartner(id);
        partner.setId(id);
        return contentPartnerRepository.save(partner);
//        ContentPartner currentPartner = contentPartnerRepository.findOne(id);
//        if (currentPartner == null){
//            throw new NotFoundException("content partner", id);
//        }
//        currentPartner.setName(partner.getName());
//        currentPartner.setAbbreviation(partner.getAbbreviation());
//        currentPartner.setDescription(partner.getDescription());
//        currentPartner.setUrl(partner.getUrl());
//        currentPartner.setLogo(null);
//        currentPartner.setLogo_type(null);
 //       return contentPartnerRepository.save(currentPartner);
    }

    public ContentPartner updateContentPartner(long id, ContentPartner partner, String logoPath) throws SQLException {
        logger.debug("Content partner Service update id "+id);
        validateContentPartner(id);
        partner.setLogo_path(logoPath);
        partner.setLogo_type(getFileExtension(logoPath));
        partner.setId(id);
        return contentPartnerRepository.save(partner);
//        ContentPartner currentPartner = contentPartnerRepository.findOne(id);
//        if (currentPartner == null){
//            throw new NotFoundException("content partner", id);
//        }
//        currentPartner.setName(partner.getName());
//        currentPartner.setAbbreviation(partner.getAbbreviation());
//        currentPartner.setDescription(partner.getDescription());
//        currentPartner.setUrl(partner.getUrl());
//        currentPartner.setLogo(logoFile);
//        currentPartner.setLogo_type(getFileExtension(logoName));
//        return contentPartnerRepository.save(currentPartner);
    }

    public List<ContentPartner> getAllContentPartners(){
        List<ContentPartner> partners = new ArrayList<>();
        contentPartnerRepository.findAll().forEach(partners::add);
        return partners;
    }

    public Collection<ContentPartner> getContentPartners(String partnerIds){
        logger.debug("Content partner service: get content partners with ids : "+partnerIds);
        if (partnerIds == null || partnerIds != null && partnerIds.length() == 0)
            throw  new NotFoundException("content partner", partnerIds);
        return contentPartnerRepository.findByIdIn(Arrays.asList(partnerIds.split("\\s*,\\s*")).stream().map(Long::valueOf).collect(Collectors.toList())).orElseThrow(
                () -> new NotFoundException("content partner", partnerIds));
    }

    public ContentPartner getContentPartner(long id){
        return contentPartnerRepository.findById(id).orElseThrow(
                () -> new NotFoundException("content partner", id));
    }

    public void validateContentPartner(long partnerId) {
        contentPartnerRepository.findById(partnerId).orElseThrow(
                () -> new NotFoundException("content partner", partnerId));
    }

    String getFileExtension(String filePath) {
        int index = filePath.lastIndexOf(File.separator);
        String fileName;
        if (index > 0) {
            fileName = filePath.substring(index + 1);
        } else
            fileName = filePath;
        int dotIndex = fileName.lastIndexOf(".");
        if (fileName.trim().length() > 0 &&  dotIndex != -1 && dotIndex != 0) {
            return fileName.substring(dotIndex + 1);
        }
         return "";
    }

}
