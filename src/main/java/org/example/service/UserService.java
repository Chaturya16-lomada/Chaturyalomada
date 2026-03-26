package org.example.service;

import org.example.entity.User;
import org.springframework.data.domain.Page;

public interface UserService {

    User createUser(User user);

    User getUserById(Long id);

    Page<User> getAllUsers(int page, int size, String sortBy);

    User updateUser(Long id, User user);

    User partialUpdateUser(Long id, User user);

    void deleteUser(Long id);
}