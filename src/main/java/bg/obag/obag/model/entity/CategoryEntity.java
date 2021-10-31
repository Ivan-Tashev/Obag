package bg.obag.obag.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "categories")
public class CategoryEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String category;
    @Column
    private Integer priority;
    @Column
    private String image;
    @ManyToOne
    private UserEntity createdBy;
    @Column
    private boolean deleted;

    public String getCategory() {
        return category;
    }

    public CategoryEntity setCategory(String category) {
        this.category = category;
        return this;
    }

    public Integer getPriority() {
        return priority;
    }

    public CategoryEntity setPriority(Integer priority) {
        this.priority = priority;
        return this;
    }

    public String getImage() {
        return image;
    }

    public CategoryEntity setImage(String image) {
        this.image = image;
        return this;
    }

    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public CategoryEntity setCreatedBy(UserEntity createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public CategoryEntity setDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }
}
