package bg.obag.obag.model.service;

import bg.obag.obag.model.entity.UserEntity;
import bg.obag.obag.model.entity.enums.Category;
import bg.obag.obag.model.entity.enums.Season;
import com.google.gson.annotations.Expose;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductServiceModel {

    private Long id;
    @Expose
    private String name;
    @Expose
    private String sku;
    @Expose
    private Category category;
    @Expose
    private Season season;
    @Expose
    private String metric;
    @Expose
    private BigDecimal cost;
    @Expose
    private BigDecimal price;
    @Expose
    private Long barcode;
    @Expose
    private String description;
    @Expose
    private String image;

    private LocalDateTime createdOn;

    private String createdBy;


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

    public String getCreatedBy() {
        return createdBy;
    }

    public ProductServiceModel setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }
}
