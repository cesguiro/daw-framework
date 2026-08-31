package es.cesguiro.daw.framework.persistence.dao.impl;

import es.cesguiro.daw.framework.core.db.TransactionManager;
import es.cesguiro.daw.framework.persistence.dao.RoleDao;
import es.cesguiro.daw.framework.persistence.dao.entity.RoleEntity;
import es.cesguiro.daw.framework.persistence.dao.mapper.RoleDaoMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class RoleDaoImpl implements RoleDao {

    private final DataSource dataSource;

    public RoleDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<RoleEntity> findByUserId(long id) {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                SELECT * FROM roles r
                         INNER JOIN user_roles ur ON r.id = ur.role_id
                         WHERE ur.user_id = ?""");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<RoleEntity> roleEntities = new java.util.ArrayList<>();
            while (resultSet.next()) {
                roleEntities.add(RoleDaoMapper.toRoleEntity(resultSet));
            }
            return roleEntities;
        } catch (Exception e) {
            throw new RuntimeException("Error al recuperar los roles del usuario de la bbdd", e);
        }
    }

    @Override
    public void saveUserRole(long userId, long roleId) {
        String sql = "INSERT INTO user_roles (user_id, role_id) VALUES (?, ?)";

        try {
            Connection connection = TransactionManager.getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, userId);
            stmt.setLong(2, roleId);
            stmt.executeUpdate();

            // Si no hay una transacción explícita en curso, cerramos el Statement y la Connection
            if (connection.getAutoCommit()) {
                stmt.close();
                connection.close();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al asociar el rol " + roleId + " al usuario " + userId, e);
        }
    }
}
