package org.example.controller;

import org.example.entity.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    // ✅ CREATE
    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        return service.createUser(user);
    }

    // ✅ GET BY ID
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return service.getUserById(id);
    }

    // ✅ GET ALL (simple)
    @GetMapping
    public List<User> getUsers() {
        return service.getAllUsers(0, 10, "id").getContent();
    }

    // ✅ PUT
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return service.updateUser(id, user);
    }

    // ✅ PATCH
    @PatchMapping("/{id}")
    public User partialUpdate(@PathVariable Long id, @Valid @RequestBody User user) {
        return service.partialUpdateUser(id, user);
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
        return "User deleted successfully";
    }
}