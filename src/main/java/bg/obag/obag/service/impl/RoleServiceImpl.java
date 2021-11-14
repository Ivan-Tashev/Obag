package bg.obag.obag.service.impl;

import bg.obag.obag.model.entity.RoleEntity;
import bg.obag.obag.model.entity.enums.RoleEnum;
import bg.obag.obag.repo.RoleRepo;
import bg.obag.obag.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepo roleRepo;

    public RoleServiceImpl(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    public void initializeRoles() {
        if (roleRepo.count() == 0) {
            RoleEntity superAdmin = new RoleEntity().setRole(RoleEnum.SUPERADMIN);
            RoleEntity admin = new RoleEntity().setRole(RoleEnum.ADMIN);
            RoleEntity user = new RoleEntity().setRole(RoleEnum.USER);
            roleRepo.saveAll(List.of(user, admin, superAdmin));
        }
    }

    @Override
    public RoleEntity findRole(RoleEnum roleEnum) {
        return roleRepo.findByRole(roleEnum);
    }

    @Override
    public List<String> findAll() {
        return roleRepo.findAll().stream()
                .sorted(Comparator.comparing(RoleEntity::getId))
                .map(roleEntity -> roleEntity.getRole().name())
                .collect(Collectors.toList());
    }
}
