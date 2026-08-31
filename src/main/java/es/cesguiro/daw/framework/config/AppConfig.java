package es.cesguiro.daw.framework.config;

import es.cesguiro.daw.framework.controller.UserController;
import es.cesguiro.daw.framework.core.AppContext;
import es.cesguiro.daw.framework.domain.service.UserService;
import es.cesguiro.daw.framework.domain.service.impl.UserServiceImpl;
import es.cesguiro.daw.framework.persistence.dao.RoleDao;
import es.cesguiro.daw.framework.persistence.dao.UserDao;
import es.cesguiro.daw.framework.persistence.dao.impl.RoleDaoImpl;
import es.cesguiro.daw.framework.persistence.dao.impl.UserDaoImpl;
import es.cesguiro.daw.framework.persistence.repository.UserRepository;
import es.cesguiro.daw.framework.persistence.repository.impl.UserRepositoryImpl;

import javax.sql.DataSource;

public class AppConfig {

    public static void configure() {
        AppContext appContext = AppContext.getInstance();
        DataSource dataSource = AppContext.getInstance().getBean(DataSource.class);

        UserDao userDao = new UserDaoImpl(dataSource);
        appContext.register(UserDao.class, userDao);
        RoleDao roleDao = new RoleDaoImpl(dataSource);
        appContext.register(RoleDao.class, roleDao);

        UserRepository userRepository = new UserRepositoryImpl(userDao, roleDao);
        appContext.register(UserRepository.class, userRepository);

        UserService userService = new UserServiceImpl(userRepository);
        appContext.register(UserService.class, userService);

        UserController userController = new UserController(userService);
        appContext.register(UserController.class, userController);

    }
}
