package bg.obag.obag.model.entity;

import bg.obag.obag.model.entity.enums.Category;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class ProductEntity extends BaseEntity{
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false, unique = true)
    private String sku;
    @ManyToOne
    private CategoryEntity category;
    @ManyToOne
    private SeasonEntity season;
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
    private UserEntity createdBy;
    @Column
    private boolean deleted;

    public String getName() {
        return name;
    }

    public ProductEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getSku() {
        return sku;
    }

    public ProductEntity setSku(String sku) {
        this.sku = sku;
        return this;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public ProductEntity setCategory(CategoryEntity category) {
        this.category = category;
        return this;
    }

    public SeasonEntity getSeason() {
        return season;
    }

    public ProductEntity setSeason(SeasonEntity season) {
        this.season = season;
        return this;
    }

    public String getMetric() {
        return metric;
    }

    public ProductEntity setMetric(String metric) {
        this.metric = metric;
        return this;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public ProductEntity setCost(BigDecimal cost) {
        this.cost = cost;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Long getBarcode() {
        return barcode;
    }

    public ProductEntity setBarcode(Long barcode) {
        this.barcode = barcode;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImage() {
        return image;
    }

    public ProductEntity setImage(String image) {
        this.image = image;
        return this;
    }

    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public ProductEntity setCreatedBy(UserEntity createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public ProductEntity setDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }
}
