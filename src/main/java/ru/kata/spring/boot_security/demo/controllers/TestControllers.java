package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.constants.RolesType;
import ru.kata.spring.boot_security.demo.helper.UserUtils;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class TestControllers {
    private final UserService userService;

    public TestControllers(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("test")
    public String showUsers(ModelMap model, Authentication authentication) {
        List<User> users = userService.getAllUsers();
        UserUtils.setViewFields(users);
        model.addAttribute("users", users);
        long myId = ((User) authentication.getPrincipal()).getId();
        User me = userService.getUserById(myId);
        model.addAttribute("my_roles", UserUtils.getRolesLine(me));
        model.addAttribute("my_email", me.getEmail());
        return "admin/admin-test";
    }

    @GetMapping("/new-user")
    public String newUser(ModelMap model) {
        model.addAttribute("aRoles", RolesType.allRolesNames);
        model.addAttribute("user", new User());
        return "admin/new-user";
    }
}
