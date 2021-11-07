package bg.obag.obag.service.impl;

import bg.obag.obag.model.entity.RoleEntity;
import bg.obag.obag.model.entity.UserEntity;
import bg.obag.obag.model.entity.enums.RoleEnum;
import bg.obag.obag.model.service.UserServiceModel;
import bg.obag.obag.repo.UserRepo;
import bg.obag.obag.security.ObagUserDetailsService;
import bg.obag.obag.service.RoleService;
import bg.obag.obag.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final ObagUserDetailsService obagUserDetailsService;

    public UserServiceImpl(UserRepo userRepo, RoleService roleService, ModelMapper modelMapper, PasswordEncoder passwordEncoder, ObagUserDetailsService obagUserDetailsService) {
        this.userRepo = userRepo;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.obagUserDetailsService = obagUserDetailsService;
    }

    @Override
    public void registerUser(UserServiceModel userServiceModel) {
        UserEntity userEntity = modelMapper.map(userServiceModel, UserEntity.class);
        userEntity
                .setPassword(passwordEncoder.encode(userServiceModel.getPassword()))
                .setRoleEntities(Set.of(roleService.findRole(RoleEnum.USER)))
                .setRegisteredOn(LocalDateTime.now())
                .setCountOrders(0)
                .setValueOrders(BigDecimal.ZERO)
                .setProducts(new ArrayList<>());

        userRepo.save(userEntity);

        // NEWLY REGISTERED USER GET LOGGED-IN.
        UserDetails principal = obagUserDetailsService.loadUserByUsername(userEntity.getEmail());

        Authentication context = new UsernamePasswordAuthenticationToken(
                principal, userEntity.getPassword(), principal.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(context);
    }

    @Override
    public UserServiceModel updateUser(UserServiceModel userServiceModel, Long id) {
        UserEntity userEntity = userRepo.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User with id " + id + " not exists."));

        userEntity.setFirstName(userServiceModel.getFirstName())
                .setLastName(userServiceModel.getLastName())
                .setPhone(userServiceModel.getPhone())
                .setEmail(userServiceModel.getEmail())
                .setPassword(passwordEncoder.encode(userServiceModel.getPassword()))
                .setCountry(userServiceModel.getCountry())
                .setPostCode(userServiceModel.getPostCode())
                .setCity(userServiceModel.getCity())
                .setAddress(userServiceModel.getAddress())
                .setPrivacyPolicy(userServiceModel.isPrivacyPolicy())
                .setNewsletter(userServiceModel.isNewsletter());

        UserEntity savedUserEntity = userRepo.save(userEntity);

        return modelMapper.map(savedUserEntity, UserServiceModel.class);
    }

    @Override
    public UserServiceModel findCurrentUserByEmail(String email) {
        UserEntity userEntity = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with emal " + email + " not exists."));
        return modelMapper.map(userEntity, UserServiceModel.class);
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
    public UserServiceModel existingEmailExceptId(String email, Long id) {
        return userRepo.findByEmailExceptId(email, id)
                .map(user -> modelMapper.map(user, UserServiceModel.class))
                .orElse(null);
    }

    @Override
    public List<String> getAllUsersEmails() {
        return userRepo.findAllUsersEmails();
    }

    @Override
    @Secured("ROLE_SUPERADMIN")
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
    @Secured("ROLE_SUPERADMIN")
    public void removeRole(String email, String role) {
        Optional<UserEntity> user = userRepo.findByEmail(email);
        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            Set<RoleEntity> roleEntities = userEntity.getRoleEntities();
            RoleEntity roleToRemove = roleService.findRole(RoleEnum.valueOf(role.toUpperCase()));

            Set<RoleEntity> updatedRoleSet = roleEntities.stream()
                    .filter(roleEntity -> !roleEntity.getRole().name().equals(roleToRemove.getRole().name()))
                    .collect(Collectors.toSet());

            userEntity.setRoleEntities(updatedRoleSet);
            userRepo.save(userEntity);
        }
    }

    @Override
    public UserServiceModel findById(Long id) {
        UserEntity userEntity = userRepo.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User with id " + id + " not exists."));
        return modelMapper.map(userEntity, UserServiceModel.class);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }


}
