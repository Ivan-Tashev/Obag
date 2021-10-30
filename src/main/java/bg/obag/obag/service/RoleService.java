package bg.obag.obag.service;

import bg.obag.obag.model.entity.RoleEntity;
import bg.obag.obag.model.entity.enums.RoleEnum;

public interface RoleService {

    void initializeRoles();

    RoleEntity findRole(RoleEnum roleEnum);
}
