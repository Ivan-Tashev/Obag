package bg.obag.obag.model.binding;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserOrderBindModel {
    private Long id;
    @NotNull
    @Size(min = 2, max = 20, message = "Името трябва да бъде от 2 до 20 символа.")
    private String firstName;
    @Size(min = 2, max = 20, message = "Фамилията трябва да бъде от 2 до 20 символа.")
    private String lastName;
    @Size(min = 6, message = "Телефонът трябва да бъде минимум 6 символа, само числа.")
    private String phone;
    @Email(message = "Въведете валиден емайл.")
    private String email;
    private String address;
    private String city;
    private String postCode;
    private String country;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public UserOrderBindModel setId(Long id) {
        this.id = id;
        return this;
    }
}
