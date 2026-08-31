package es.cesguiro.daw.framework.controller;

import es.cesguiro.daw.framework.core.http.Response;
import es.cesguiro.daw.framework.domain.model.User;
import es.cesguiro.daw.framework.domain.service.UserService;

import java.util.List;

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public Response findAll(){
        List<User> users = userService.findAll();
        return Response.ok(users);
    }

    public Response findById(long id) {
        User user = userService.findById(id);
        return Response.ok(user);
    }

    public Response create(User user) {
        // Lógica para crear un nuevo usuario
        IO.println("Creando usuario: " + user);
        return Response.created(user);
    }
}
