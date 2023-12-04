package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.helper.UserUtils;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminControllers {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    private List<User> usersCached;
    private User adminCached;

    public AdminControllers(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @GetMapping
    public String showUsers(ModelMap model, Authentication authentication) {
        usersCached = userService.getAllUsers();
        UserUtils.setUsersAgeAndRoles(usersCached);
        long myId = ((User) authentication.getPrincipal()).getId();
        adminCached = userService.getUserById(myId);
        model.addAttribute("allRolesNames", UserUtils.allRolesNames());
        model.addAttribute("users", usersCached);
        model.addAttribute("my_roles", UserUtils.getRolesLine(adminCached));
        model.addAttribute("my_email", adminCached.getEmail());
        return "admin/admin";
    }

    @GetMapping("/about-user/{id}")
    public String showUser(@PathVariable long id, ModelMap model) {
        model.addAttribute("users", usersCached);
        User user = userService.getUserById(id);
        UserUtils.setUserAgeAndRoles(user);
        model.addAttribute("user", user);
        model.addAttribute("my_roles", UserUtils.getRolesLine(adminCached));
        model.addAttribute("my_email", adminCached.getEmail());
        return "admin/about-user";
    }

    @GetMapping("/new-user")
    public String newUser(ModelMap model) {
        model.addAttribute("users", usersCached);
        model.addAttribute("allRoles", UserUtils.allRoles());
        model.addAttribute("user", new User());
        model.addAttribute("my_roles", UserUtils.getRolesLine(adminCached));
        model.addAttribute("my_email", adminCached.getEmail());
        return "admin/new-user";
    }

    @PostMapping("/save-user")
    public String saveUser(@ModelAttribute("user") User user, Authentication authentication) {
        String emailFromForm = user.getEmail();
        User userFromDb = userService.getUserByEmail(emailFromForm);
        if (userFromDb == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            long myId = ((User) authentication.getPrincipal()).getId();
            user.setParentAdminId(myId);
            userService.saveUser(user);
        }
        return "redirect:/admin";
    }

    @PutMapping("/change-ban/{id}")
    public String changeUserBan(@PathVariable long id) {
        User user = userService.getUserById(id);
        user.setLocked(!user.isLocked());
        userService.updateUser(user);
        return "redirect:/admin";
    }
}
