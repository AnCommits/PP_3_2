package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.helper.UserUtils;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/user")
public class UserControllers {
    private final UserService userService;

    public UserControllers(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showUser(ModelMap model, Authentication authentication) {
        User loggedInUser = (User) authentication.getPrincipal();
        User updatedUser = userService.getUserById(loggedInUser.getId());
        UserUtils.setDatesFields(updatedUser);
        model.addAttribute("title", "Моя страница");
        model.addAttribute("user", updatedUser);
        return "user";
    }
}
