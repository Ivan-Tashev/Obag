package bg.obag.obag.model.service;

import bg.obag.obag.model.entity.UserEntity;

import java.time.LocalDateTime;

public class CategoryServiceModel {

    private Long id;
    private String category;
    private Integer priority;
    private String image;
    private LocalDateTime createdOn;
    private String createdBy;
    private boolean deleted;

    public Long getId() {
        return id;
    }

    public CategoryServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public CategoryServiceModel setCategory(String category) {
        this.category = category;
        return this;
    }

    public Integer getPriority() {
        return priority;
    }

    public CategoryServiceModel setPriority(Integer priority) {
        this.priority = priority;
        return this;
    }

    public String getImage() {
        return image;
    }

    public CategoryServiceModel setImage(String image) {
        this.image = image;
        return this;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public CategoryServiceModel setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public CategoryServiceModel setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public CategoryServiceModel setDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }
}
