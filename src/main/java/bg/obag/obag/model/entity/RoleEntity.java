package bg.obag.obag.model.entity;


import bg.obag.obag.model.entity.enums.RoleEnum;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING )
    private RoleEnum role;

    public RoleEntity() {
    }

    public Long getId() {
        return id;
    }

    public RoleEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public RoleEntity(RoleEnum role) {
        this.role = role;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }
}
