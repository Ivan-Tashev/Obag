package bg.obag.obag.model.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "logs")
public class LogEntity extends BaseEntity{
    @ManyToOne
    private UserEntity user;
    @ManyToOne
    private ProductEntity product;
    private String action;

    public UserEntity getUser() {
        return user;
    }

    public LogEntity setUser(UserEntity user) {
        this.user = user;
        return this;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public LogEntity setProduct(ProductEntity product) {
        this.product = product;
        return this;
    }

    public String getAction() {
        return action;
    }

    public LogEntity setAction(String action) {
        this.action = action;
        return this;
    }
}
