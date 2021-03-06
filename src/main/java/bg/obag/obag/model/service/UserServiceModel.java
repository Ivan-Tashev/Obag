package bg.obag.obag.model.service;

import bg.obag.obag.model.entity.ProductEntity;
import bg.obag.obag.model.entity.RoleEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class UserServiceModel {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String password;
    private String address;
    private String city;
    private String postCode;
    private String country;
    private RoleEntity roleEntity;
    private LocalDateTime registeredOn;
    private Integer countOrders;
    private BigDecimal valueOrders;
    private String note;
    private boolean privacyPolicy;
    private boolean newsletter;
    private List<ProductEntity> productEntities;

    public Long getId() {
        return id;
    }

    public UserServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserServiceModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserServiceModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public UserServiceModel setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserServiceModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserServiceModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public UserServiceModel setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getCity() {
        return city;
    }

    public UserServiceModel setCity(String city) {
        this.city = city;
        return this;
    }

    public String getPostCode() {
        return postCode;
    }

    public UserServiceModel setPostCode(String postCode) {
        this.postCode = postCode;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public UserServiceModel setCountry(String country) {
        this.country = country;
        return this;
    }

    public RoleEntity getRole() {
        return roleEntity;
    }

    public UserServiceModel setRole(RoleEntity roleEntity) {
        this.roleEntity = roleEntity;
        return this;
    }

    public LocalDateTime getRegisteredOn() {
        return registeredOn;
    }

    public UserServiceModel setRegisteredOn(LocalDateTime registeredOn) {
        this.registeredOn = registeredOn;
        return this;
    }

    public Integer getCountOrders() {
        return countOrders;
    }

    public UserServiceModel setCountOrders(Integer countOrders) {
        this.countOrders = countOrders;
        return this;
    }

    public BigDecimal getValueOrders() {
        return valueOrders;
    }

    public UserServiceModel setValueOrders(BigDecimal valueOrders) {
        this.valueOrders = valueOrders;
        return this;
    }

    public String getNote() {
        return note;
    }

    public UserServiceModel setNote(String note) {
        this.note = note;
        return this;
    }

    public List<ProductEntity> getItems() {
        return productEntities;
    }

    public UserServiceModel setItems(List<ProductEntity> productEntities) {
        this.productEntities = productEntities;
        return this;
    }

    public boolean isNewsletter() {
        return newsletter;
    }

    public UserServiceModel setNewsletter(boolean newsletter) {
        this.newsletter = newsletter;
        return this;
    }

    public boolean isPrivacyPolicy() {
        return privacyPolicy;
    }

    public UserServiceModel setPrivacyPolicy(boolean privacyPolicy) {
        this.privacyPolicy = privacyPolicy;
        return this;
    }
}
