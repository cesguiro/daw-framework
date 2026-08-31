package es.cesguiro.daw.framework.persistence.dao;

import es.cesguiro.daw.framework.persistence.dao.entity.RoleEntity;

import java.util.List;

public interface RoleDao {

    List<RoleEntity> findByUserId(long id);

    void saveUserRole(long userId, long roleId);
}
