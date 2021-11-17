package bg.obag.obag.model.service;

import java.time.LocalDateTime;

public class SeasonServiceModel {

    private Long id;
    private String season;
    private Integer priority;
    private String image;
    private LocalDateTime createdOn;
    private String createdBy;
    private boolean deleted;

    public Long getId() {
        return id;
    }

    public SeasonServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getSeason() {
        return season;
    }

    public SeasonServiceModel setSeason(String season) {
        this.season = season;
        return this;
    }

    public Integer getPriority() {
        return priority;
    }

    public SeasonServiceModel setPriority(Integer priority) {
        this.priority = priority;
        return this;
    }

    public String getImage() {
        return image;
    }

    public SeasonServiceModel setImage(String image) {
        this.image = image;
        return this;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public SeasonServiceModel setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public SeasonServiceModel setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public SeasonServiceModel setDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }
}
