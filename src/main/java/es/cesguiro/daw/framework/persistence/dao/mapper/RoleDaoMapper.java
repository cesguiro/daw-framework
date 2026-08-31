package es.cesguiro.daw.framework.persistence.dao.mapper;

import es.cesguiro.daw.framework.persistence.dao.entity.RoleEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleDaoMapper {

    public static RoleEntity toRoleEntity(ResultSet resultSet) throws SQLException {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(resultSet.getLong("id"));
        roleEntity.setName(resultSet.getString("name"));
        return roleEntity;
    }
}
