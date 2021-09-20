package bg.obag.obag.service;

import bg.obag.obag.model.entity.Role;
import bg.obag.obag.model.entity.enums.RoleEnum;

public interface RoleService {

    void initializeRoles();

    Role findRole(RoleEnum roleEnum);
}
