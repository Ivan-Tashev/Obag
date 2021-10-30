package bg.obag.obag.service.impl;

import bg.obag.obag.model.entity.RoleEntity;
import bg.obag.obag.model.entity.enums.RoleEnum;
import bg.obag.obag.repo.RoleRepo;
import bg.obag.obag.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepo roleRepo;

    public RoleServiceImpl(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    public void initializeRoles() {
        if(roleRepo.count() == 0) {
            final RoleEntity superAdmin = new RoleEntity(RoleEnum.SUPERADMIN);
            final RoleEntity admin = new RoleEntity(RoleEnum.ADMIN);
            final RoleEntity user = new RoleEntity(RoleEnum.USER);
            roleRepo.saveAll(List.of(user, admin, superAdmin));
        }
    }

    @Override
    public RoleEntity findRole(RoleEnum roleEnum) {
        return roleRepo.findByRole(roleEnum);
    }
}
