package es.cesguiro.daw.framework.persistence.repository.impl;

import es.cesguiro.daw.framework.domain.model.Role;
import es.cesguiro.daw.framework.domain.model.User;
import es.cesguiro.daw.framework.persistence.dao.RoleDao;
import es.cesguiro.daw.framework.persistence.dao.UserDao;
import es.cesguiro.daw.framework.persistence.dao.entity.UserEntity;
import es.cesguiro.daw.framework.persistence.repository.UserRepository;
import es.cesguiro.daw.framework.persistence.repository.mapper.RoleRepositoryMapper;
import es.cesguiro.daw.framework.persistence.repository.mapper.UserRepositoryMapper;

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
}
