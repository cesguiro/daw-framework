package es.cesguiro.daw.framework.routes;

import es.cesguiro.daw.framework.controller.UserController;
import es.cesguiro.daw.framework.core.AppContext;
import es.cesguiro.daw.framework.core.routing.Router;

public class ApiRoutes {

    public static Router configure() {
        Router router = new Router();
        UserController userController = AppContext.getInstance().getBean(UserController.class);

        router.get("/users", request -> userController.findAll())
                .get("/users/detail", request -> {
                    String idParam = request.getQueryParam("id");
                    if (idParam == null || idParam.isEmpty()) {
                        throw new IllegalArgumentException("El parámetro 'id' es obligatorio");
                    }
                    long id = Long.parseLong(idParam);
                    return userController.findById(id);
                });

        return router;
    }
}