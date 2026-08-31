package es.cesguiro.daw.framework.domain.service.impl;

import es.cesguiro.daw.framework.core.exception.ResourceNotFoundException;
import es.cesguiro.daw.framework.domain.model.User;
import es.cesguiro.daw.framework.domain.service.UserService;
import es.cesguiro.daw.framework.persistence.repository.UserRepository;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
    }
}
