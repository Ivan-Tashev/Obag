package bg.obag.obag.model.binding;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductAddBindingModel {
    private Long id;
    @NotBlank(message = "product name must be unique, required.")
    private String name;
    @NotBlank(message = "sku must be unique, required.")
    private String sku;
    @NotEmpty(message = "category is required.")
    private String category;
    @NotEmpty(message = "season is required.")
    private String season;
    private String metric;
    @NotNull(message = "cost must be zero or positive, required.")
    @DecimalMin(value = "0", message = "cost must be 0 or positive, required.")
    private BigDecimal cost;
    @NotNull(message = "price must be positive, required.")
    @DecimalMin(value = "0", message = "price must be positive, required.")
    private BigDecimal price;
    private Long barcode;
    @NotEmpty(message = "description is required.")
    private String description;
    private String image;
    @DateTimeFormat(pattern = "dd.mm.yyyy, HH:ss")
    private LocalDateTime createdOn;
    private String createdBy;
    private boolean deleted;

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

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public ProductAddBindingModel setCreatedOn(LocalDateTime createdOn) {
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

    public boolean isDeleted() {
        return deleted;
    }

    public ProductAddBindingModel setDeleted(boolean deleted) {
        this.deleted = deleted;
        return this;
    }
}
