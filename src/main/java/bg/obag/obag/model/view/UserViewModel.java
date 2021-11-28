package bg.obag.obag.model.view;

public class UserViewModel {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String address;
    private String city;
    private String postCode;
    private String country;

    public String getFirstName() {
        return firstName;
    }

    public UserViewModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserViewModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public UserViewModel setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserViewModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public UserViewModel setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getCity() {
        return city;
    }

    public UserViewModel setCity(String city) {
        this.city = city;
        return this;
    }

    public String getPostCode() {
        return postCode;
    }

    public UserViewModel setPostCode(String postCode) {
        this.postCode = postCode;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public UserViewModel setCountry(String country) {
        this.country = country;
        return this;
    }
}
