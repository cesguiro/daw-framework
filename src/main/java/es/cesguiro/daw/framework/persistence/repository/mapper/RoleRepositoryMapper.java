package es.cesguiro.daw.framework.persistence.repository.mapper;

import es.cesguiro.daw.framework.domain.model.Role;
import es.cesguiro.daw.framework.persistence.dao.entity.RoleEntity;

public class RoleRepositoryMapper {

    public static Role toRole(RoleEntity roleEntity) {
        Role role = new Role();
        role.setId(roleEntity.getId());
        role.setName(roleEntity.getName());
        return role;
    }
}
