package es.cesguiro.daw.framework.persistence.dao.impl;

import es.cesguiro.daw.framework.persistence.dao.UserDao;
import es.cesguiro.daw.framework.persistence.dao.entity.UserEntity;
import es.cesguiro.daw.framework.persistence.dao.mapper.UserDaoMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private final DataSource dataSource;

    public UserDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<UserEntity> findAll() {
        String sql = "SELECT * FROM users";
        try(Connection connection = dataSource.getConnection()) {
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            List<UserEntity> userEntities = new ArrayList<>();
            while (resultSet.next()) {
                userEntities.add(UserDaoMapper.toUserEntity(resultSet));
            }
            return userEntities;
        } catch (Exception e) {
            throw new RuntimeException("Error al recuperar los usuarios de la bbdd", e);
        }
    }

    @Override
    public Optional<UserEntity> findById(long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(UserDaoMapper.toUserEntity(resultSet));
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException("Error al recuperar el usuario de la bbdd", e);
        }
    }
}
