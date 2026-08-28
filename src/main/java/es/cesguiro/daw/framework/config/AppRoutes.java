package es.cesguiro.daw.framework.config;

import es.cesguiro.daw.framework.controller.UserController;
import es.cesguiro.daw.framework.routing.Router;

public class AppRoutes {

    public static Router register() {
        Router router = new Router();

        // Instanciar o recuperar controladores (en versiones posteriores con IoC/Reflection será automático)
        UserController userController = new UserController();

        // Registro de rutas sintáctico (estilo Laravel)
        router.get("/api/users", userController::findAll)
                .get("/api/users/detail", userController::findById);

        return router;
    }
}
