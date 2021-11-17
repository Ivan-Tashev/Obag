package bg.obag.obag.model.binding;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class SeasonBindModel {
    private Long id;
    @NotEmpty(message = "")
    @Size(min = 2, max = 20, message =  "season must be between 2 and 20 characters, required.")
    private String season;
    @Positive(message = "priority must be positive, 1 is highest.")
    private Integer priority;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdOn;
    private String createdBy;
    private String image;
    private boolean deleted;

    public Long getId() {
        return id;
    }

    public SeasonBindModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getSeason() {
        return season;
    }

    public SeasonBindModel setSeason(String season) {
        this.season = season;
        return this;
    }

    public Integer getPriority() {
        return priority;
    }

    public SeasonBindModel setPriority(Integer priority) {
        this.priority = priority;
        return this;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public SeasonBindModel setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public SeasonBindModel setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public String getImage() {
        return image;
    }

    public SeasonBindModel setImage(String image) {
        this.image = image;
        return this;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public SeasonBindModel setDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }
}
