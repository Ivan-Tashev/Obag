package bg.obag.obag.model.view;

import bg.obag.obag.model.entity.enums.Category;
import bg.obag.obag.model.entity.enums.Season;

import java.math.BigDecimal;

public class ProductViewModel {
    private Long id;
    private String name;
    private String sku;
    private Category category;
    private Season season;
    private String metric;
    private BigDecimal price;
    private Long barcode;
    private String description;
    private String image;

    public Long getId() {
        return id;
    }

    public ProductViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getSku() {
        return sku;
    }

    public ProductViewModel setSku(String sku) {
        this.sku = sku;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public ProductViewModel setCategory(Category category) {
        this.category = category;
        return this;
    }

    public Season getSeason() {
        return season;
    }

    public ProductViewModel setSeason(Season season) {
        this.season = season;
        return this;
    }

    public String getMetric() {
        return metric;
    }

    public ProductViewModel setMetric(String metric) {
        this.metric = metric;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductViewModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Long getBarcode() {
        return barcode;
    }

    public ProductViewModel setBarcode(Long barcode) {
        this.barcode = barcode;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImage() {
        return image;
    }

    public ProductViewModel setImage(String image) {
        this.image = image;
        return this;
    }
}
