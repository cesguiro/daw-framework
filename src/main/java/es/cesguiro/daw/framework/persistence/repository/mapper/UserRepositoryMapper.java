package es.cesguiro.daw.framework.persistence.repository.mapper;

import es.cesguiro.daw.framework.domain.model.User;
import es.cesguiro.daw.framework.persistence.dao.entity.UserEntity;

public class UserRepositoryMapper {

    public static User toUser(UserEntity userEntity) {
        User user = new User();
        user.setId(userEntity.getId());
        user.setEmail(userEntity.getEmail());
        user.setPassword(userEntity.getPassword());
        return user;
    }
}
