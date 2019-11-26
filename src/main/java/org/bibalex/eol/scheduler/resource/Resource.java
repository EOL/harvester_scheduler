/**
 * Created by hduser on 12/6/17.
 */

package org.bibalex.eol.scheduler.resource;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.bibalex.eol.scheduler.content_partner.ContentPartner;
import org.bibalex.eol.scheduler.harvest.Harvest;
import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "Resource")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "harvestResource_sp",
                procedureName = "harvestResource",
                resultClasses = { Resource.class },
                parameters = {
                        @StoredProcedureParameter(
                                name = "cDate",
                                type = Date.class,
                                mode = ParameterMode.IN) }),
        @NamedStoredProcedureQuery(
                name = "getHarvestedResources_sp",
                procedureName = "getHarvestedResources",
                parameters = {
                        @StoredProcedureParameter(
                                name = "cDate",
                                type = Timestamp.class,
                                mode = ParameterMode.IN) })
})

public class Resource {

    public enum Type {
        url,
        file
    }

    public enum HarvestFrequency{
        once,
        weekly,
        monthly,
        bimonthly,
        quarterly
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "origin_url")
    private String originUrl;

    @Column(name = "uploaded_url")
    private String uploadedUrl;

    @Enumerated(EnumType.STRING)
    private Resource.Type type;

    private String path;

    @Column(name = "last_harvested_at")
    private Date lastHarvestedAt;

    @Convert(converter = HarvestFreqConverter.class)
    @Column(name = "harvest_frequency")
    private Resource.HarvestFrequency harvestFrequency;

    @Range(min = 0, max = 31)
    @Column(name = "day_of_month")
    private int dayOfMonth = 0;

    @Column(name = "nodes_count")
    private int nodesCount;

    private int position = -1;

    @Column(name = "is_paused")
    private boolean paused = false;

    @Column(name = "is_harvest_inprogress")
    private boolean harvestInprogress = false;

    @Column(name = "is_approved")
    private boolean approved = false;

    @Column(name = "is_trusted")
    private boolean trusted = false;

    @Column(name = "forced_internally")
    private boolean forcedInternally = false;

    @Column(name = "is_autopublished")
    private boolean autopublished = false;

    @Column(name = "is_forced")
    private boolean forced = false;

    @Column(name = "dataset_license")
    private int datasetLicense = 47;

    @Column(name = "dataset_rights_statement")
    private String datasetRightsStatement;

    @Column(name = "dataset_rights_holder")
    private String datasetRightsHolder;

    @Column(name = "default_license_string")
    private int defaultLicenseString;

    @Column(name = "defaultRightsStatement")
    private String defaultRightsStatement;

    @Column(name = "default_rights_holder")
    private String defaultRightsHolder;

    @Column(name = "default_language_id")
    private int defaultLanguageId = 152;

    @ManyToOne
    @JoinColumn (name = "content_partner_id")
    @JsonBackReference
    private ContentPartner contentPartner;

    @OneToMany(mappedBy = "resource")
    private Set<Harvest> harvests = new HashSet<>();

    public Resource(){
    }

    public Resource(long id){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public String getUploadedUrl() {
        return uploadedUrl;
    }

    public Type getType() {
        return type;
    }

    public String getPath() {
        return path;
    }

    public Date getLastHarvestedAt() {
        return lastHarvestedAt;
    }

    public HarvestFrequency getHarvestFrequency() {
        return harvestFrequency;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public int getNodesCount() {
        return nodesCount;
    }

    public int getPosition() {
        return position;
    }

    public boolean isPaused() {
        return paused;
    }

    public boolean isHarvestInprogress() {
        return harvestInprogress;
    }

    public boolean isApproved() {
        return approved;
    }

    public boolean isTrusted() {
        return trusted;
    }

    public boolean isForcedInternally() {
        return forcedInternally;
    }

    public boolean isAutopublished() {
        return autopublished;
    }

    public boolean isForced() {
        return forced;
    }

    public int getDatasetLicense() {
        return datasetLicense;
    }

    public String getDatasetRightsStatement() {
        return datasetRightsStatement;
    }

    public String getDatasetRightsHolder() {
        return datasetRightsHolder;
    }

    public int getDefaultLicenseString() {
        return defaultLicenseString;
    }

    public String getDefaultRightsStatement() {
        return defaultRightsStatement;
    }

    public String getDefaultRightsHolder() {
        return defaultRightsHolder;
    }

    public int getDefaultLanguageId() {
        return defaultLanguageId;
    }

    public ContentPartner getContentPartner() {
        return contentPartner;
    }

    public Set<Harvest> getHarvests() {
        return harvests;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
    }

    public void setUploadedUrl(String uploadedUrl) {
        this.uploadedUrl = uploadedUrl;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setLastHarvestedAt(Date lastHarvestedAt) {
        this.lastHarvestedAt = lastHarvestedAt;
    }

    public void setHarvestFrequency(HarvestFrequency harvestFrequency) {
        this.harvestFrequency = harvestFrequency;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public void setNodesCount(int nodesCount) {
        this.nodesCount = nodesCount;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void setHarvestInprogress(boolean harvestInprogress) {
        this.harvestInprogress = harvestInprogress;
    }

    public void setApproved(boolean isApproved) {
        this.approved = isApproved;
    }

    public void setTrusted(boolean trusted) {
        this.trusted = trusted;
    }

    public void setForcedInternally(boolean forcedInternally) {
        this.forcedInternally = forcedInternally;
    }

    public void setAutopublished(boolean autopublished) {
       this.autopublished = autopublished;
    }

    public void setForced(boolean forced) {
        this.forced = forced;
    }

    public void setDatasetLicense(int datasetLicense) {
        this.datasetLicense = datasetLicense;
    }

    public void setDatasetRightsStatement(String datasetRightsStatement) {
        this.datasetRightsStatement = datasetRightsStatement;
    }

    public void setDatasetRightsHolder(String datasetRightsHolder) {
        this.datasetRightsHolder = datasetRightsHolder;
    }

    public void setDefaultLicenseString(int defaultLicenseString) {
        this.defaultLicenseString = defaultLicenseString;
    }

    public void setDefaultRightsStatement(String defaultRightsStatement) {
        this.defaultRightsStatement = defaultRightsStatement;
    }

    public void setDefaultRightsHolder(String defaultRightsHolder) {
        this.defaultRightsHolder = defaultRightsHolder;
    }

    public void setDefaultLanguageId(int defaultLanguageId) {
        this.defaultLanguageId = defaultLanguageId;
    }

    public void setContentPartner(ContentPartner contentPartner) {
        this.contentPartner = contentPartner;
    }

    public void setHarvests(Set<Harvest> harvests) {
        this.harvests = harvests;
    }

}