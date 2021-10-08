package bg.obag.obag.model.entity;

import bg.obag.obag.model.entity.enums.Category;
import bg.obag.obag.model.entity.enums.Season;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Blob;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product extends BaseEntity{
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false, unique = true)
    private String sku;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Enumerated(EnumType.STRING)
    private Season season;
    @Column
    private String metric;
    @Column(nullable = false)
    private BigDecimal cost;
    @Column(nullable = false)
    private BigDecimal price;
    @Column
    private Long barcode;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
    @Column(nullable = false)
    private String image;
    @ManyToOne
    private User createdBy;

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public String getSku() {
        return sku;
    }

    public Product setSku(String sku) {
        this.sku = sku;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public Product setCategory(Category category) {
        this.category = category;
        return this;
    }

    public Season getSeason() {
        return season;
    }

    public Product setSeason(Season season) {
        this.season = season;
        return this;
    }

    public String getMetric() {
        return metric;
    }

    public Product setMetric(String metric) {
        this.metric = metric;
        return this;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public Product setCost(BigDecimal cost) {
        this.cost = cost;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Product setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Long getBarcode() {
        return barcode;
    }

    public Product setBarcode(Long barcode) {
        this.barcode = barcode;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Product setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImage() {
        return image;
    }

    public Product setImage(String image) {
        this.image = image;
        return this;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public Product setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
        return this;
    }
}
