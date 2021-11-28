package bg.obag.obag.model.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "carts")
public class CartEntity extends BaseEntity {
    @OneToMany
    private List<ProductEntity> products;
    @Column
    BigDecimal deliveryCost; // calculated based on products
    @Column
    BigDecimal totalValue; // calculated based on products
    @OneToOne(optional = true)
    UserEntity user;

    public List<ProductEntity> getProducts() {
        return products;
    }

    public CartEntity setProducts(List<ProductEntity> products) {
        this.products = products;
        return this;
    }

    public BigDecimal getDeliveryCost() {
        return deliveryCost;
    }

    public CartEntity setDeliveryCost(BigDecimal deliveryCost) {
        this.deliveryCost = deliveryCost;
        return this;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public CartEntity setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public CartEntity setUser(UserEntity user) {
        this.user = user;
        return this;
    }
}
