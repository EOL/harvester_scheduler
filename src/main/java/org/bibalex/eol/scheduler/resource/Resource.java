package org.bibalex.eol.scheduler.resource;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.bibalex.eol.scheduler.content_partner.ContentPartner;
import org.bibalex.eol.scheduler.harvest.Harvest;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "Resource")
@NamedStoredProcedureQuery(
        name = "harvestResource_sp",
        procedureName = "harvestResource",
//        resultClasses = {Resource.class },
        parameters = {
                @StoredProcedureParameter(name = "cDate", mode = ParameterMode.IN, type = Date.class)
        }
)


public class Resource implements Serializable{

    private enum Type {
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
    private String origin_url;
    private String uploaded_url;
    @Enumerated(EnumType.STRING)
    private Type type;
    private String path;
    private Date last_harvested_at;
    @Convert(converter = HarvestFreqConverter.class)
    private HarvestFrequency harvest_frequency;
    @Range(min = 0, max = 31)
    private int day_of_month=0;
    private int nodes_count;
    private int position = -1;
    private boolean is_paused = false;
    private boolean is_approved = false;
    private boolean is_trusted = false;
    private boolean is_autopublished = false;
    private boolean is_forced = false;
    private int dataset_license = 47;
    private String dataset_rights_statement;
    private String dataset_rights_holder;
    private int default_license_string;
    private String default_rights_statement;
    private String default_rights_holder;
    private int default_language_id = 152;
    @ManyToOne
    @JoinColumn (name="content_partner_id")
    @JsonBackReference
    private ContentPartner contentPartner;
    @OneToMany(mappedBy ="resource")
    private Set<Harvest> harvests = new HashSet<>();

    public  Resource(){
    }

    public  Resource(long id){
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

    public String getOrigin_url() {
        return origin_url;
    }

    public void setOrigin_url(String origin_url) {
        this.origin_url = origin_url;
    }

    public String getUploaded_url() {
        return uploaded_url;
    }

    public void setUploaded_url(String uploaded_url) {
        this.uploaded_url = uploaded_url;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getLast_harvested_at() {
        return last_harvested_at;
    }

    public void setLast_harvested_at(Date last_harvested_at) {
        this.last_harvested_at = last_harvested_at;
    }

    public HarvestFrequency getHarvest_frequency() {
        return harvest_frequency;
    }

    public void setHarvest_frequency(HarvestFrequency harvest_frequency) {
        this.harvest_frequency = harvest_frequency;
    }

    public int getDay_of_month() {
        return day_of_month;
    }

    public void setDay_of_month(int day_of_month) {
        this.day_of_month = day_of_month;
    }

    public int getNodes_count() {
        return nodes_count;
    }

    public void setNodes_count(int nodes_count) {
        this.nodes_count = nodes_count;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean is_paused() {
        return is_paused;
    }

    public void setIs_paused(boolean is_paused) {
        this.is_paused = is_paused;
    }

    public boolean is_approved() {
        return is_approved;
    }

    public void setIs_approved(boolean is_approved) {
        this.is_approved = is_approved;
    }

    public boolean is_trusted() {
        return is_trusted;
    }

    public void setIs_trusted(boolean is_trusted) {
        this.is_trusted = is_trusted;
    }

    public boolean is_autopublished() {
        return is_autopublished;
    }

    public void setIs_autopublished(boolean is_autopublished) {
        this.is_autopublished = is_autopublished;
    }

    public boolean is_forced() {
        return is_forced;
    }

    public void setIs_forced(boolean is_forced) {
        this.is_forced = is_forced;
    }

    public int getDataset_license() {
        return dataset_license;
    }

    public void setDataset_license(int dataset_license) {
        this.dataset_license = dataset_license;
    }

    public String getDataset_rights_statement() {
        return dataset_rights_statement;
    }

    public void setDataset_rights_statement(String dataset_rights_statement) {
        this.dataset_rights_statement = dataset_rights_statement;
    }

    public String getDataset_rights_holder() {
        return dataset_rights_holder;
    }

    public void setDataset_rights_holder(String dataset_rights_holder) {
        this.dataset_rights_holder = dataset_rights_holder;
    }

    public int getDefault_license_string() {
        return default_license_string;
    }

    public void setDefault_license_string(int default_license_string) {
        this.default_license_string = default_license_string;
    }

    public String getDefault_rights_statement() {
        return default_rights_statement;
    }

    public void setDefault_rights_statement(String default_rights_statement) {
        this.default_rights_statement = default_rights_statement;
    }

    public String getDefault_rights_holder() {
        return default_rights_holder;
    }

    public void setDefault_rights_holder(String default_rights_holder) {
        this.default_rights_holder = default_rights_holder;
    }

    public int getDefault_language_id() {
        return default_language_id;
    }

    public void setDefault_language_id(int default_language_id) {
        this.default_language_id = default_language_id;
    }

    public ContentPartner getContentPartner() {
        return contentPartner;
    }

    public void setContentPartner(ContentPartner contentPartner) {
        this.contentPartner = contentPartner;
    }

    public Set<Harvest> getHarvests() {
        return harvests;
    }

    public void setHarvests(Set<Harvest> harvests) {
        this.harvests = harvests;
    }

    @Override
    public String toString() {
        return "Res:"  +harvest_frequency.toString();
    }


}
