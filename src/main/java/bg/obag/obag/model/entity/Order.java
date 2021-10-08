package bg.obag.obag.model.entity;

import bg.obag.obag.model.entity.enums.Payment;
import bg.obag.obag.model.entity.enums.Status;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity{
    @Column(nullable = false)
    private LocalDateTime date;
    @ManyToMany
    private List<Product> products;
    @Column(name = "total_value", nullable = false)
    private Integer totalValue;
    @ManyToOne
    private User user;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "tracking_number")
    private String trackingNumber;
    @Enumerated(EnumType.STRING)
    private Payment paymentMethod;
    @Column(columnDefinition = "TEXT")
    private String note;

    public LocalDateTime getDate() {
        return date;
    }

    public Order setDate(LocalDateTime date) {
        this.date = date;
        return this;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Order setProducts(List<Product> products) {
        this.products = products;
        return this;
    }

    public Integer getTotalValue() {
        return totalValue;
    }

    public Order setTotalValue(Integer totalValue) {
        this.totalValue = totalValue;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Order setUser(User user) {
        this.user = user;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public Order setStatus(Status status) {
        this.status = status;
        return this;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public Order setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
        return this;
    }

    public Payment getPaymentMethod() {
        return paymentMethod;
    }

    public Order setPaymentMethod(Payment paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public String getNote() {
        return note;
    }

    public Order setNote(String note) {
        this.note = note;
        return this;
    }
}
