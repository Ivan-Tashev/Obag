package bg.obag.obag.model.binding;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserLoginBindingModel {
    @Email(message = "Въведете валиден емайл.")
    private String email;
    @NotNull
    @Size(min = 3, max = 20, message = "Паролата трябва да бъде от 3 до 20 символа.")
    private String password;

    public String getEmail() {
        return email;
    }

    public UserLoginBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserLoginBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }
}
