package es.cesguiro.daw.framework.controller;

import es.cesguiro.daw.framework.domain.model.Role;
import es.cesguiro.daw.framework.domain.model.User;

import java.util.List;

public class UserController {

    private final Role adminRole = new Role(1, "ADMIN");
    private final Role userRole = new Role(2, "USER");
    private final List<User> users = List.of(
            new User(1L, "admin@example.com", "1234", List.of(adminRole, userRole)),
            new User(2L, "user1@example.com", "password1", List.of(userRole))
    );

    public List<User> findAll(){
        return users;
    }

    public User findById(long id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
