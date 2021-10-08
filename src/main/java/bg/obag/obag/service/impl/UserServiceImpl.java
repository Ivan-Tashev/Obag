package bg.obag.obag.service.impl;

import bg.obag.obag.model.entity.User;
import bg.obag.obag.model.entity.enums.RoleEnum;
import bg.obag.obag.model.service.UserServiceModel;
import bg.obag.obag.repo.UserRepo;
import bg.obag.obag.security.CurrentUser;
import bg.obag.obag.service.RoleService;
import bg.obag.obag.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final RoleService roleService;
    private final CurrentUser currentUser;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepo userRepo, RoleService roleService, CurrentUser currentUser, ModelMapper modelMapper) {
        this.userRepo = userRepo;
        this.roleService = roleService;
        this.currentUser = currentUser;
        this.modelMapper = modelMapper;
    }

    @Override
    public void registerUser(UserServiceModel userServiceModel) {
        User user = modelMapper.map(userServiceModel, User.class);
        user.setRole(roleService.findRole(RoleEnum.USER));
        user.setRegisteredOn(LocalDateTime.now());
        user.setCountOrders(0);
        user.setValueOrders(BigDecimal.ZERO);
        user.setProducts(new ArrayList<>());

        userRepo.save(user);
    }

    @Override
    public UserServiceModel findByEmailAndPassword(String email, String password) {
        return userRepo.findByEmailAndPassword(email, password)
                .map(user -> modelMapper.map(user, UserServiceModel.class))
                .orElse(null);
    }

    @Override
    public void logoutUser() {
        currentUser.setId(null)
                .setFirstName(null)
                .setEmail(null)
                .setRole(null);
    }

    @Override
    public UserServiceModel existingEmail(String email) {
        return userRepo.findByEmail(email)
                .map(user -> modelMapper.map(user, UserServiceModel.class))
                .orElse(null);
    }

    @Override
    public void loginUser(UserServiceModel loggedInUser) {
        currentUser.setId(loggedInUser.getId())
                .setFirstName(loggedInUser.getFirstName())
                .setEmail(loggedInUser.getEmail())
                .setRole(loggedInUser.getRole());
    }

    @Override
    public List<String> getAllUsersEmails() {
        return userRepo.findAllUsersEmails();
    }

    @Override
    public void changeRole(String email, String role) {
        Optional<User> user = userRepo.findByEmail(email);
        if (user.isPresent()) {
            user.get().setRole(roleService.findRole(RoleEnum.valueOf(role.toUpperCase())));
            userRepo.save(user.get());
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepo.findById(currentUser.getId());
    }
}
