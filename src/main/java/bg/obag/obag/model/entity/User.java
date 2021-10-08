package bg.obag.obag.model.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseEntity{
    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;
    @Column(name = "last_name", length = 20)
    private String lastName;
    @Column(length = 20)
    private String phone;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, length = 30)
    private String password;
    @Column(length = 50)
    private String address;
    @Column(length = 20)
    private String city;
    @Column(name = "post_code", length = 20)
    private String postCode;
    @Column(length = 20)
    private String country;
    @ManyToOne
    private Role role;
    @Column(name = "registered_on")
    private LocalDateTime registeredOn;
    @Column(name = "count_of_orders")
    private Integer countOrders;
    @Column(name = "value_orders")
    private BigDecimal valueOrders;
    @Column(columnDefinition = "TEXT")
    private String note;
    @ManyToMany
    private List<Product> products;

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public User setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getCity() {
        return city;
    }

    public User setCity(String city) {
        this.city = city;
        return this;
    }

    public String getPostCode() {
        return postCode;
    }

    public User setPostCode(String postCode) {
        this.postCode = postCode;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public User setCountry(String country) {
        this.country = country;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDateTime getRegisteredOn() {
        return registeredOn;
    }

    public User setRegisteredOn(LocalDateTime registeredOn) {
        this.registeredOn = registeredOn;
        return this;
    }

    public Integer getCountOrders() {
        return countOrders;
    }

    public User setCountOrders(Integer countOrders) {
        this.countOrders = countOrders;
        return this;
    }

    public BigDecimal getValueOrders() {
        return valueOrders;
    }

    public User setValueOrders(BigDecimal valueOrders) {
        this.valueOrders = valueOrders;
        return this;
    }

    public String getNote() {
        return note;
    }

    public User setNote(String note) {
        this.note = note;
        return this;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
