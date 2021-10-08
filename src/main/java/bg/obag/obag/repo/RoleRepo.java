package bg.obag.obag.repo;


import bg.obag.obag.model.entity.Role;
import bg.obag.obag.model.entity.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {

    Role findByRole(RoleEnum role);
}
