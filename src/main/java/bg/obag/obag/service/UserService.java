package bg.obag.obag.service;

import bg.obag.obag.model.entity.UserEntity;
import bg.obag.obag.model.service.UserServiceModel;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void registerUser(UserServiceModel userServiceModel);

    UserServiceModel findByEmailAndPassword(String email, String password);

    UserServiceModel existingEmail(String email);

    UserServiceModel existingEmailExceptId(String email, Long id);

    List<String> getAllUsersEmails();

    void changeRole(String email, String role);

    void removeRole(String email, String role);

    UserServiceModel findById(Long id);

    Optional<UserEntity> findByEmail(String name);

    UserServiceModel updateUser(UserServiceModel userServiceModel, Long id);

    UserServiceModel findCurrentUserByEmail(String name);

}

