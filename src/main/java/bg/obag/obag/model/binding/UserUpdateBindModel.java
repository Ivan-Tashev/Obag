package bg.obag.obag.model.binding;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserUpdateBindModel {
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
    @NotNull
    @Size(min = 3, max = 20, message = "Паролата трябва да бъде от 3 до 20 символа.")
    private String password;
    @Size(min = 3, max = 20, message = "Паролите не съвпадат")
    private String rePassword;
    private String address;
    private String city;
    private String postCode;
    private String country;
    @NotNull
    private boolean privacyPolicy;
    private boolean newsletter;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
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

    public boolean isPrivacyPolicy() {
        return privacyPolicy;
    }

    public UserUpdateBindModel setPrivacyPolicy(boolean privacyPolicy) {
        this.privacyPolicy = privacyPolicy;
        return this;
    }

    public boolean isNewsletter() {
        return newsletter;
    }

    public UserUpdateBindModel setNewsletter(boolean newsletter) {
        this.newsletter = newsletter;
        return this;
    }

    public Long getId() {
        return id;
    }

    public UserUpdateBindModel setId(Long id) {
        this.id = id;
        return this;
    }
}
