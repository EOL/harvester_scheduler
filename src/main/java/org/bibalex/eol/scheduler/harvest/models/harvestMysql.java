package org.bibalex.eol.scheduler.harvest.models;

import java.io.Serializable;

public class harvestMysql implements Serializable {
    private Integer id;
    private Integer resource_id;
    private String media_status;

    public harvestMysql(Integer id, Integer resource_id, String media_status){
        this.setId(id);
        this.setResource_id(resource_id);
        this.setMedia_status(media_status);

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getResource_id() {
        return resource_id;
    }

    public void setResource_id(Integer resource_id) {
        this.resource_id = resource_id;
    }

    public String getMedia_status() {
        return media_status;
    }

    public void setMedia_status(String media_status) {
        this.media_status = media_status;
    }
}


