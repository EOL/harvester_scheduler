package org.bibalex.eol.scheduler.content_partner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.bibalex.eol.scheduler.resource.Resource;

@Entity
@JsonIgnoreProperties
public class ContentPartner implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String abbreviation;

    private String url;

    private String description;

    @Column(name="logo_path")
    private String logoPath;

    @Column(name="logo_type")
    private String logoType;

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

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logo) {
        this.logoPath = logo;
    }

    public void setResources(Set<Resource> resources) {
        this.resources = resources;
    }

    public String getLogoType() {
        return logoType;
    }

    public void setLogoType(String logo_type) {
        this.logoType = logo_type;
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("name: "+getName()+ "\nabbreviation: "+getAbbreviation()+ "\nDescription: "+getDescription() +"\nurl: "+getUrl());
        return str.toString();
    }
}
