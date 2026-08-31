package es.cesguiro.daw.framework.persistence.repository;

import es.cesguiro.daw.framework.domain.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll();

    Optional<User> findById(long id);
}
