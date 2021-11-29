package bg.obag.obag.model.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity{
    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;
    @Column(name = "last_name", length = 20)
    private String lastName;
    @Column(length = 20)
    private String phone;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(length = 50)
    private String address;
    @Column(length = 20)
    private String city;
    @Column(name = "post_code", length = 20)
    private String postCode;
    @Column(length = 20)
    private String country;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<RoleEntity> roleEntities;
    @Column(name = "registered_on")
    private LocalDateTime registeredOn;
    @Column(name = "count_of_orders")
    private Integer countOrders;
    @Column(name = "value_orders")
    private BigDecimal valueOrders;
    @Column(columnDefinition = "TEXT")
    private String note;
    @Column
    private boolean privacyPolicy;
    @Column
    private boolean newsletter;
    @ManyToMany
    private List<ProductEntity> productEntities;

    public String getFirstName() {
        return firstName;
    }

    public UserEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public UserEntity setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public UserEntity setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getCity() {
        return city;
    }

    public UserEntity setCity(String city) {
        this.city = city;
        return this;
    }

    public String getPostCode() {
        return postCode;
    }

    public UserEntity setPostCode(String postCode) {
        this.postCode = postCode;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public UserEntity setCountry(String country) {
        this.country = country;
        return this;
    }

    public List<RoleEntity> getRoleEntities() {
        return roleEntities;
    }

    public UserEntity setRoleEntities(List<RoleEntity> roleEntities) {
        this.roleEntities = roleEntities;
        return this;
    }

    public List<ProductEntity> getProductEntities() {
        return productEntities;
    }

    public UserEntity setProductEntities(List<ProductEntity> productEntities) {
        this.productEntities = productEntities;
        return this;
    }

    public LocalDateTime getRegisteredOn() {
        return registeredOn;
    }

    public UserEntity setRegisteredOn(LocalDateTime registeredOn) {
        this.registeredOn = registeredOn;
        return this;
    }

    public Integer getCountOrders() {
        return countOrders;
    }

    public UserEntity setCountOrders(Integer countOrders) {
        this.countOrders = countOrders;
        return this;
    }

    public BigDecimal getValueOrders() {
        return valueOrders;
    }

    public UserEntity setValueOrders(BigDecimal valueOrders) {
        this.valueOrders = valueOrders;
        return this;
    }

    public String getNote() {
        return note;
    }

    public UserEntity setNote(String note) {
        this.note = note;
        return this;
    }

    public List<ProductEntity> getProducts() {
        return productEntities;
    }

    public void setProducts(List<ProductEntity> productEntities) {
        this.productEntities = productEntities;
    }

    public boolean isPrivacyPolicy() {
        return privacyPolicy;
    }

    public UserEntity setPrivacyPolicy(boolean privacyPolicy) {
        this.privacyPolicy = privacyPolicy;
        return this;
    }

    public boolean isNewsletter() {
        return newsletter;
    }

    public UserEntity setNewsletter(boolean newsletter) {
        this.newsletter = newsletter;
        return this;
    }
}
