package bg.obag.obag.service;

import bg.obag.obag.model.entity.User;
import bg.obag.obag.model.service.UserServiceModel;
import bg.obag.obag.security.CurrentUser;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void registerUser(UserServiceModel userServiceModel);

    UserServiceModel findByEmailAndPassword(String email, String password);

    void logoutUser();

    UserServiceModel existingEmail(String email);

    void loginUser(UserServiceModel loggedInUser);

    List<String> getAllUsersEmails();

    void changeRole(String email, String role);

    Optional<User> findById(Long id);
}

