package bg.obag.obag.model.service;

import bg.obag.obag.model.view.ProductCategoryViewModel;
import bg.obag.obag.model.view.UserViewModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartServiceModel {
    private Long id;
    private List<ProductCategoryViewModel> products = new ArrayList<>();
    private BigDecimal deliveryCost;
    private BigDecimal totalValue;
    private UserViewModel user;

    public Long getId() {
        return id;
    }

    public CartServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public List<ProductCategoryViewModel> getProducts() {
        return products;
    }

    public CartServiceModel setProducts(List<ProductCategoryViewModel> products) {
        this.products = products;
        return this;
    }

    public BigDecimal getDeliveryCost() {
        return deliveryCost;
    }

    public CartServiceModel setDeliveryCost(BigDecimal deliveryCost) {
        this.deliveryCost = deliveryCost;
        return this;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public CartServiceModel setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
        return this;
    }

    public UserViewModel getUser() {
        return user;
    }

    public CartServiceModel setUser(UserViewModel user) {
        this.user = user;
        return this;
    }
}
