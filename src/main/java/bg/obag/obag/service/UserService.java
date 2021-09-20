package bg.obag.obag.service;

import bg.obag.obag.model.service.UserServiceModel;

public interface UserService {

    void registerUser(UserServiceModel userServiceModel);

    UserServiceModel findByEmailAndPassword(String email, String password);

    void logoutUser();

    boolean existingEmail(String email);
}

