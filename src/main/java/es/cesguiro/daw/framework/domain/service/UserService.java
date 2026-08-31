package es.cesguiro.daw.framework.domain.service;

import es.cesguiro.daw.framework.domain.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(long id);

    User save(User user);
}
