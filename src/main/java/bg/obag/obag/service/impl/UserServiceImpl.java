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
        user.setItems(new ArrayList<>());

        userRepo.save(user);
    }

    @Override
    public UserServiceModel findByEmailAndPassword(String email, String password) {
        User user = userRepo.findByEmailAndPassword(email, password);
        return modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public void logoutUser() {
        currentUser.setId(null)
                .setFirstName(null)
                .setEmail(null)
                .setRole(null);
    }

    @Override
    public boolean existingEmail(String email) {
        return userRepo.findByEmail(email) == null;
    }
}
