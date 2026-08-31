package es.cesguiro.daw.framework.persistence.dao.mapper;

import es.cesguiro.daw.framework.persistence.dao.entity.UserEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoMapper {

    public static UserEntity toUserEntity(ResultSet resultSet) throws SQLException {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(resultSet.getLong("id"));
        userEntity.setEmail(resultSet.getString("email"));
        userEntity.setPassword(resultSet.getString("password"));
        return userEntity;
    }
}
