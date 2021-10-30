package bg.obag.obag.service;

import bg.obag.obag.model.entity.UserEntity;
import bg.obag.obag.model.service.UserServiceModel;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void registerUser(UserServiceModel userServiceModel);

    UserServiceModel findByEmailAndPassword(String email, String password);

    UserServiceModel existingEmail(String email);

    List<String> getAllUsersEmails();

    void changeRole(String email, String role);

    Optional<UserEntity> findById(Long id);
}

