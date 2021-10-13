package bg.obag.obag.model.view;

import bg.obag.obag.model.entity.enums.Category;

import java.math.BigDecimal;

public class ProductCategoryViewModel {
    private Long id;
    private String name;
    private Category category;
    private BigDecimal price;
    private String image;

    public Long getId() {
        return id;
    }

    public ProductCategoryViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductCategoryViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public ProductCategoryViewModel setCategory(Category category) {
        this.category = category;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductCategoryViewModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getImage() {
        return image;
    }

    public ProductCategoryViewModel setImage(String image) {
        this.image = image;
        return this;
    }
}
