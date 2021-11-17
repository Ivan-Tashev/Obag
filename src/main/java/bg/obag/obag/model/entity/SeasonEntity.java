package bg.obag.obag.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "seasons")
public class SeasonEntity extends BaseEntity{

    @Column(nullable = false, unique = true)
    private String season;
    @Column
    private Integer priority;
    @Column
    private String image;
    @ManyToOne
    private UserEntity createdBy;
    @Column
    private boolean deleted;

    public String getSeason() {
        return season;
    }

    public SeasonEntity setSeason(String season) {
        this.season = season;
        return this;
    }

    public Integer getPriority() {
        return priority;
    }

    public SeasonEntity setPriority(Integer priority) {
        this.priority = priority;
        return this;
    }

    public String getImage() {
        return image;
    }

    public SeasonEntity setImage(String image) {
        this.image = image;
        return this;
    }

    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public SeasonEntity setCreatedBy(UserEntity createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public SeasonEntity setDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }
}
