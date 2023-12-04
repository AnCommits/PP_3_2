package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@RestController
@RequestMapping("/api/user")
public class RestControllers {

    private final UserService userService;

    public RestControllers(UserService userService) {
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

    @PutMapping("/update")
    public void updateUser(@RequestBody User user, Authentication authentication) {
        long myId = ((User) authentication.getPrincipal()).getId();
        user.setParentAdminId(myId);
        userService.updateUser(user);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.removeUserById(id);
    }
}
