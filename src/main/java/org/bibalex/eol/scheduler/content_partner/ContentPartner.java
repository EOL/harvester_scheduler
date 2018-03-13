package org.bibalex.eol.scheduler.content_partner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.bibalex.eol.scheduler.resource.Resource;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@JsonIgnoreProperties
public class ContentPartner implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String abbreviation;
    private String url;
    private String description;
    private String logo_path;
    private String logo_type;
    private Date created_at;
    private Date updated_at;
    @OneToMany(mappedBy ="contentPartner")
    private Set<Resource> resources = new HashSet<>();

    public ContentPartner(long id){
        this.id = id;
    }
    public ContentPartner(){}

    public Set<Resource> getResources() {
        return resources;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogo_path() {
        return logo_path;
    }

    public void setLogo_path(String logo) {
        this.logo_path = logo;
    }

    public void setResources(Set<Resource> resources) {
        this.resources = resources;
    }

    public String getLogo_type() {
        return logo_type;
    }

    public void setLogo_type(String logo_type) {
        this.logo_type = logo_type;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("name: "+getName()+ "\nabbreviation: "+getAbbreviation()+ "\nDescription: "+getDescription() +"\nurl: "+getUrl());
        return str.toString();
    }
}
