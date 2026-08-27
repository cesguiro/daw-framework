package es.cesguiro.daw.framework.controller;

import es.cesguiro.daw.framework.http.Request;
import es.cesguiro.daw.framework.http.Response;

import java.util.List;
import java.util.Map;

public class UserController {

    public Response findAll(Request request) {
        List<String> users = List.of("Alice", "Bob", "Charlie");
        return Response.ok(users);
    }

    public Response findById(Request request) {
        String id = request.getQueryParam("id");
        if (id == null) {
            return Response.badRequest(Map.of("error", "El parámetro 'id' es obligatorio"));
        }
        return Response.ok("Usuario encontrado con ID: " + id);
    }
}
