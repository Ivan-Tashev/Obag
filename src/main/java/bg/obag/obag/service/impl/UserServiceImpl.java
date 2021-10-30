package bg.obag.obag.service.impl;

import bg.obag.obag.model.entity.UserEntity;
import bg.obag.obag.model.entity.enums.RoleEnum;
import bg.obag.obag.model.service.UserServiceModel;
import bg.obag.obag.repo.UserRepo;
import bg.obag.obag.service.RoleService;
import bg.obag.obag.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepo userRepo, RoleService roleService, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(UserServiceModel userServiceModel) {
        UserEntity userEntity = modelMapper.map(userServiceModel, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(userServiceModel.getPassword()));
        userEntity.setRoleEntities(Set.of(roleService.findRole(RoleEnum.USER)));
        userEntity.setRegisteredOn(LocalDateTime.now());
        userEntity.setCountOrders(0);
        userEntity.setValueOrders(BigDecimal.ZERO);
        userEntity.setProducts(new ArrayList<>());

        userRepo.save(userEntity);
    }

    @Override
    public UserServiceModel findByEmailAndPassword(String email, String password) {
        return userRepo.findByEmailAndPassword(email, password)
                .map(user -> modelMapper.map(user, UserServiceModel.class))
                .orElse(null);
    }


    @Override
    public UserServiceModel existingEmail(String email) {
        return userRepo.findByEmail(email)
                .map(user -> modelMapper.map(user, UserServiceModel.class))
                .orElse(null);
    }

    @Override
    public List<String> getAllUsersEmails() {
        return userRepo.findAllUsersEmails();
    }

    @Override
    public void changeRole(String email, String role) {
        Optional<UserEntity> user = userRepo.findByEmail(email);
        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            userEntity.getRoleEntities()
                    .add(roleService.findRole(
                            RoleEnum.valueOf(role.toUpperCase())));
            userRepo.save(userEntity);
        }
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }
}
