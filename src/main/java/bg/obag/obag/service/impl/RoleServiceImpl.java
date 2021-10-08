package bg.obag.obag.service.impl;

import bg.obag.obag.model.entity.Role;
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
            final Role superAdmin = new Role(RoleEnum.SUPERADMIN);
            final Role admin = new Role(RoleEnum.ADMIN);
            final Role user = new Role(RoleEnum.USER);
            roleRepo.saveAll(List.of(user, admin, superAdmin));
        }
    }

    @Override
    public Role findRole(RoleEnum roleEnum) {
        return roleRepo.findByRole(roleEnum);
    }
}
