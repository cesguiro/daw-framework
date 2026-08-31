package es.cesguiro.daw.framework.controller;

import es.cesguiro.daw.framework.core.exception.ResourceNotFoundException;
import es.cesguiro.daw.framework.core.http.Response;
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

    public Response findAll(){
        return Response.ok(users);
    }

    public Response findById(long id) {
        User user = users.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
        if (user == null) {
            throw new ResourceNotFoundException("Usuario con id " + id + " no encontrado");
        }
        return Response.ok(user);
    }
}
