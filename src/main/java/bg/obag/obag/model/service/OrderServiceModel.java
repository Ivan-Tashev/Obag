package bg.obag.obag.model.service;

import bg.obag.obag.model.entity.UserEntity;
import bg.obag.obag.model.entity.enums.Payment;
import bg.obag.obag.model.entity.enums.Status;
import bg.obag.obag.model.view.ProductCategoryViewModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderServiceModel {
    private Long id;
    private LocalDateTime createdOn;
    private List<ProductCategoryViewModel> products;
    private BigDecimal totalValue;
    private UserEntity user;
    private Status status;
    private String trackingNumber;
    private Payment paymentMethod;
    private String note;

    public Long getId() {
        return id;
    }

    public OrderServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public OrderServiceModel setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public List<ProductCategoryViewModel> getProducts() {
        return products;
    }

    public OrderServiceModel setProducts(List<ProductCategoryViewModel> products) {
        this.products = products;
        return this;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public OrderServiceModel setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public OrderServiceModel setUser(UserEntity user) {
        this.user = user;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public OrderServiceModel setStatus(Status status) {
        this.status = status;
        return this;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public OrderServiceModel setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
        return this;
    }

    public Payment getPaymentMethod() {
        return paymentMethod;
    }

    public OrderServiceModel setPaymentMethod(Payment paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public String getNote() {
        return note;
    }

    public OrderServiceModel setNote(String note) {
        this.note = note;
        return this;
    }
}
