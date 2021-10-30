package bg.obag.obag.model.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ProductAddBindingModel {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String sku;
    @NotNull
    private String category;
    @NotNull
    private String season;
    private String metric;
    @NotNull
    private BigDecimal cost;
    @NotNull
    private BigDecimal price;
    private Long barcode;
    @NotNull
    private String description;
    private String image;
    private String createdOn;
    private String createdBy;

    public Long getId() {
        return id;
    }

    public ProductAddBindingModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductAddBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getSku() {
        return sku;
    }

    public ProductAddBindingModel setSku(String sku) {
        this.sku = sku;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public ProductAddBindingModel setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getSeason() {
        return season;
    }

    public ProductAddBindingModel setSeason(String season) {
        this.season = season;
        return this;
    }

    public String getMetric() {
        return metric;
    }

    public ProductAddBindingModel setMetric(String metric) {
        this.metric = metric;
        return this;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public ProductAddBindingModel setCost(BigDecimal cost) {
        this.cost = cost;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductAddBindingModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Long getBarcode() {
        return barcode;
    }

    public ProductAddBindingModel setBarcode(Long barcode) {
        this.barcode = barcode;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductAddBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImage() {
        return image;
    }

    public ProductAddBindingModel setImage(String image) {
        this.image = image;
        return this;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public ProductAddBindingModel setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public ProductAddBindingModel setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }
}
