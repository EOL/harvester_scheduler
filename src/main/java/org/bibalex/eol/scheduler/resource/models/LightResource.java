/**
 * Created by maha.mostafa on 2/25/18.
 */

package org.bibalex.eol.scheduler.resource.models;

import java.util.Date;
import org.bibalex.eol.scheduler.resource.Resource;

public interface LightResource {

    Long getId();

    String getName();

    String getOriginUrl();

    String getUploadedUrl();

    Resource.Type getType();

    String getPath();

    Date getLastHarvestedAt();

    Resource.HarvestFrequency getHarvestFrequency();

    int getDayOfMonth();

    int getNodesCount();

    int getPosition();

    boolean isPaused();

    boolean isHarvestInprogress();

    boolean isApproved();

    boolean isTrusted();

    boolean isForcedInternally();

    boolean isAutopublished();

    boolean isForced();

    int getDatasetLicense();

    String getDatasetRightsStatement();

    String getDatasetRightsHolder();

    int getDefaultLicenseString();

    String getDefaultRightsStatement();

    String getDefaultRightsHolder();

    int getDefaultLanguageId();

}
