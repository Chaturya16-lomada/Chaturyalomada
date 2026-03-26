package org.example.service;

import org.example.entity.User;
import org.example.exception.ResourceNotFoundException;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    public User createUser(User user) {
        return repository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public Page<User> getAllUsers(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return repository.findAll(pageable);
    }

    public User updateUser(Long id, User user) {
        User existing = getUserById(id);
        existing.setName(user.getName());
        existing.setEmail(user.getEmail());
        existing.setAge(user.getAge());
        return repository.save(existing);
    }
    @Override
    public User partialUpdateUser(Long id, User user) {

        User existingUser = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getName() != null) {
            existingUser.setName(user.getName());
        }

        if (user.getEmail() != null) {
            existingUser.setEmail(user.getEmail());
        }

        if (user.getAge() != 0) {
            existingUser.setAge(user.getAge());
        }

        return repository.save(existingUser);
    }
    public User patchUser(Long id, Map<String, Object> updates) {
        User user = getUserById(id);

        updates.forEach((key, value) -> {
            Field field = org.springframework.util.ReflectionUtils.findField(User.class, key);
            if (field != null) {
                field.setAccessible(true);
                org.springframework.util.ReflectionUtils.setField(field, user, value);
            }
        });

        return repository.save(user);
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        repository.delete(user);
    }
}