package bg.obag.obag.model.entity;

import bg.obag.obag.model.entity.enums.Payment;
import bg.obag.obag.model.entity.enums.Status;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderEntity extends BaseEntity{
    @ManyToMany
    private List<ProductEntity> productEntities;
    @Column(name = "total_value", nullable = false)
    private BigDecimal totalValue;
    @ManyToOne(optional = true)
    private UserEntity user;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "tracking_number")
    private String trackingNumber;
    @Enumerated(EnumType.STRING)
    private Payment paymentMethod;
    @Column(columnDefinition = "TEXT")
    private String note;


    public List<ProductEntity> getProducts() {
        return productEntities;
    }

    public OrderEntity setProducts(List<ProductEntity> productEntities) {
        this.productEntities = productEntities;
        return this;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public OrderEntity setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public OrderEntity setUser(UserEntity userEntity) {
        this.user = userEntity;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public OrderEntity setStatus(Status status) {
        this.status = status;
        return this;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public OrderEntity setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
        return this;
    }

    public Payment getPaymentMethod() {
        return paymentMethod;
    }

    public OrderEntity setPaymentMethod(Payment paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public String getNote() {
        return note;
    }

    public OrderEntity setNote(String note) {
        this.note = note;
        return this;
    }
}
