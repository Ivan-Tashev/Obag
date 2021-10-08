package bg.obag.obag.model.service;

import bg.obag.obag.model.entity.User;
import bg.obag.obag.model.entity.enums.Category;
import bg.obag.obag.model.entity.enums.Season;

import java.math.BigDecimal;
import java.sql.Blob;
import java.time.LocalDateTime;

public class ProductServiceModel {
    private Long id;
    private String name;
    private String sku;
    private Category category;
    private Season season;
    private String metric;
    private BigDecimal cost;
    private BigDecimal price;
    private Long barcode;
    private String description;
    private String image;
    private LocalDateTime createdOn;
    private User createdBy;

    public Long getId() {
        return id;
    }

    public ProductServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getSku() {
        return sku;
    }

    public ProductServiceModel setSku(String sku) {
        this.sku = sku;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public ProductServiceModel setCategory(Category category) {
        this.category = category;
        return this;
    }

    public Season getSeason() {
        return season;
    }

    public ProductServiceModel setSeason(Season season) {
        this.season = season;
        return this;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public ProductServiceModel setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public String getMetric() {
        return metric;
    }

    public ProductServiceModel setMetric(String metric) {
        this.metric = metric;
        return this;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public ProductServiceModel setCost(BigDecimal cost) {
        this.cost = cost;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductServiceModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Long getBarcode() {
        return barcode;
    }

    public ProductServiceModel setBarcode(Long barcode) {
        this.barcode = barcode;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImage() {
        return image;
    }

    public ProductServiceModel setImage(String image) {
        this.image = image;
        return this;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public ProductServiceModel setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
        return this;
    }
}
