package org.bibalex.eol.scheduler.harvest;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.bibalex.eol.scheduler.resource.Resource;

import javax.persistence.*;
import java.util.Date;


@Entity
public class Harvest {

    public enum State{
        succeed,
        failed,
        running,
        pending
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Date start_at;
    private Date validated_at;
    private Date deltas_created_at;
    private Date completed_at;

    @Enumerated(EnumType.STRING)
    private Harvest.State state;
    @ManyToOne
    @JoinColumn (name="resource_id")
    @JsonBackReference
    private Resource resource;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Date getStart_at() {
        return start_at;
    }

    public void setStart_at(Date start_at) {
        this.start_at = start_at;
    }

    public Date getValidated_at() {
        return validated_at;
    }

    public void setValidated_at(Date validated_at) {
        this.validated_at = validated_at;
    }

    public Date getDeltas_created_at() {
        return deltas_created_at;
    }

    public void setDeltas_created_at(Date deltas_created_at) {
        this.deltas_created_at = deltas_created_at;
    }

    public Date getCompleted_at() {
        return completed_at;
    }

    public void setCompleted_at(Date completed_at) {
        this.completed_at = completed_at;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    Harvest.State getHarvestStatus(String harvestStr) {
        switch (harvestStr) {
            case "succeed":
                return Harvest.State.succeed;
            case "failed":
                return Harvest.State.failed;
            case "running":
                return Harvest.State.running;
            case "pending":
                return Harvest.State.pending;
            default:
                throw new IllegalArgumentException("Unknown " + harvestStr);
        }
    }
}
