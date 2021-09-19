package bg.obag.obag.model.entity;

import bg.obag.obag.model.entity.enums.Category;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "items")
public class Item extends BaseEntity{
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false, unique = true)
    private String sku;
    @Column(nullable = false)
    private Category category;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private String image;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    public String getName() {
        return name;
    }

    public Item setName(String name) {
        this.name = name;
        return this;
    }

    public String getSku() {
        return sku;
    }

    public Item setSku(String sku) {
        this.sku = sku;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public Item setCategory(Category category) {
        this.category = category;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Item setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getImage() {
        return image;
    }

    public Item setImage(String image) {
        this.image = image;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Item setDescription(String description) {
        this.description = description;
        return this;
    }
}
