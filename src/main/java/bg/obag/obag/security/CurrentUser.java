package bg.obag.obag.security;

import bg.obag.obag.model.entity.Role;
import bg.obag.obag.model.entity.enums.RoleEnum;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class CurrentUser {
    private Long id;
    private String firstName;
    private String email;
    private Role role;

    public Long getId() {
        return id;
    }

    public CurrentUser setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public CurrentUser setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public CurrentUser setEmail(String email) {
        this.email = email;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public CurrentUser setRole(Role role) {
        this.role = role;
        return this;
    }

    public boolean isAnonymous() {
        return this.email == null;
    }

    public boolean isAdmin() {
        if (this.role != null) {
            return this.role.getRole() == RoleEnum.SUPERADMIN || this.role.getRole() == RoleEnum.ADMIN;
        }
        return false;
    }
}
