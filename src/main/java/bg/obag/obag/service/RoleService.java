package bg.obag.obag.service;

import bg.obag.obag.model.entity.RoleEntity;
import bg.obag.obag.model.entity.enums.RoleEnum;

import java.util.List;

public interface RoleService {

    void initializeRoles();

    RoleEntity findRole(RoleEnum roleEnum);

    List<String> findAll();
}
