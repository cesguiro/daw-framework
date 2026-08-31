package es.cesguiro.daw.framework.persistence.repository.impl;

import es.cesguiro.daw.framework.core.AppContext;
import es.cesguiro.daw.framework.core.db.TransactionManager;
import es.cesguiro.daw.framework.domain.model.Role;
import es.cesguiro.daw.framework.domain.model.User;
import es.cesguiro.daw.framework.persistence.dao.RoleDao;
import es.cesguiro.daw.framework.persistence.dao.UserDao;
import es.cesguiro.daw.framework.persistence.dao.entity.UserEntity;
import es.cesguiro.daw.framework.persistence.repository.UserRepository;
import es.cesguiro.daw.framework.persistence.repository.mapper.RoleRepositoryMapper;
import es.cesguiro.daw.framework.persistence.repository.mapper.UserRepositoryMapper;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    private final UserDao userDao;
    private final RoleDao roleDao;

    public UserRepositoryImpl(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Override
    public List<User> findAll() {
        List<UserEntity> userEntities = userDao.findAll();
        List<User> users = new ArrayList<>();
        for (UserEntity userEntity : userEntities) {
            User user = UserRepositoryMapper.toUser(userEntity);
            List<Role> roles = roleDao.findByUserId(user.getId()).stream()
                    .map(RoleRepositoryMapper::toRole)
                    .toList();
            user.setRoles(roles);
            users.add(user);
        }
        return users;
    }

    @Override
    public Optional<User> findById(long id) {
        return userDao.findById(id)
                .map(userEntity -> {
                    User user = UserRepositoryMapper.toUser(userEntity);
                    List<Role> roles = roleDao.findByUserId(user.getId()).stream()
                            .map(RoleRepositoryMapper::toRole)
                            .toList();
                    user.setRoles(roles);
                    return user;
                });
    }

    @Override
    public User save(User user) {
        try {
            // 1. Inicia la transacción ligada a este hilo
            TransactionManager.beginTransaction();

            // 2. Operaciones transparentes (ambas usan LA MISMA conexión internamente)
            UserEntity userEntity = UserRepositoryMapper.toUserEntity(user);
            UserEntity savedUserEntity = userDao.create(userEntity);

            for (Role role : user.getRoles()) {
                roleDao.saveUserRole(savedUserEntity.getId(), role.getId());
            }

            // 3. Confirmar cambios si todo fue bien
            TransactionManager.commit();

            User userSaved = UserRepositoryMapper.toUser(savedUserEntity);
            userSaved.setRoles(user.getRoles());

            return userSaved;

        } catch (Exception e) {
            // 4. Revertir cambios ante cualquier fallo
            TransactionManager.rollback();
            throw new RuntimeException("Error al guardar el usuario de forma transaccional. Rollback realizado.", e);
        }
    }
}
