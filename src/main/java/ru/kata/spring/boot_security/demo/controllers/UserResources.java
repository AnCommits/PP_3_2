package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserResources {

    private final UserService userService;

    @Autowired
    public UserResources(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return user;
        }
        throw new RuntimeException("User with " + id + " not found");
    }

    @PostMapping("/save")
    public void saveUser(@RequestBody User user) {
        System.out.println("saveUser " + user);
//        return userService.save(user);
    }
}
