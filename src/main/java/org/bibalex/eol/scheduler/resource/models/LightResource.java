package org.bibalex.eol.scheduler.resource.models;

import org.bibalex.eol.scheduler.content_partner.ContentPartner;
import org.bibalex.eol.scheduler.harvest.Harvest;
import org.bibalex.eol.scheduler.resource.Resource;

import java.util.Date;
import java.util.Set;

/**
 * Created by maha.mostafa on 2/25/18.
 */
public interface LightResource {

    public Long getId();

    public String getName();


    public String getOriginUrl();

    public String getUploadedUrl();

    public Resource.Type getType();

    public String getPath();

    public Date getLastHarvestedAt();

    public Resource.HarvestFrequency getHarvestFrequency();

    public int getDayOfMonth();

    public int getNodesCount();

    public int getPosition();

    public boolean isPaused();

    public boolean isHarvestInprogress();

    public boolean isApproved();

    public boolean isTrusted();

    public boolean isForcedInternally();

    public boolean isAutopublished();

    public boolean isForced();

    public int getDatasetLicense();

    public String getDatasetRightsStatement();

    public String getDatasetRightsHolder();

    public int getDefaultLicenseString();

    public String getDefaultRightsStatement();

    public String getDefaultRightsHolder();

    public int getDefaultLanguageId();


}
