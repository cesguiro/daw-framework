package es.cesguiro.daw.framework.persistence.dao;

import es.cesguiro.daw.framework.persistence.dao.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    List<UserEntity> findAll();

    Optional<UserEntity> findById(long id);
}
