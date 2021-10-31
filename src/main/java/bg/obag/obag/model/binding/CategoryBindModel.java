package bg.obag.obag.model.binding;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class CategoryBindModel {
    private Long id;
    @NotEmpty(message = "")
    @Size(min = 2, max = 20, message =  "category must be between 2 and 20 characters, required.")
    private String category;
    @Positive(message = "priority must be positive, 1 is highest.")
    private Integer priority;
    @DateTimeFormat(pattern = "dd.mm.yyyy,HH:ss")
    private LocalDateTime createdOn;
    private String createdBy;
    private String image;
    private boolean deleted;


    public Long getId() {
        return id;
    }

    public CategoryBindModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public CategoryBindModel setCategory(String category) {
        this.category = category;
        return this;
    }

    public Integer getPriority() {
        return priority;
    }

    public CategoryBindModel setPriority(Integer priority) {
        this.priority = priority;
        return this;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public CategoryBindModel setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public CategoryBindModel setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public String getImage() {
        return image;
    }

    public CategoryBindModel setImage(String image) {
        this.image = image;
        return this;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public CategoryBindModel setDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }
}
