package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.helper.UserUtils;
import ru.kata.spring.boot_security.demo.models.Role;
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
    private User userRepeatEdit;
    private String pw;
    private StringBuilder message;

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
        model.addAttribute("users", usersCached);
        model.addAttribute("my_roles", UserUtils.getRolesLine(adminCached));
        model.addAttribute("my_email", adminCached.getEmail());
        return "admin/admin";
    }

    @GetMapping("/about-user/{id}")
    public String showUser(@PathVariable long id, ModelMap model) {
        model.addAttribute("users", usersCached);
        model.addAttribute("id", id);
        model.addAttribute("my_roles", UserUtils.getRolesLine(adminCached));
        model.addAttribute("my_email", adminCached.getEmail());
        return "admin/about-user";
    }

    @GetMapping("/new-user")
    public String newUser(ModelMap model) {
        model.addAttribute("users", usersCached);
        model.addAttribute("aRoles", UserUtils.allRoles());
        model.addAttribute("user", new User());
        model.addAttribute("my_roles", UserUtils.getRolesLine(adminCached));
        model.addAttribute("my_email", adminCached.getEmail());
        return "admin/new-user";
    }

//    @GetMapping
//    public String showAllUsers(ModelMap model) {
//        List<User> users = userService.getAllUsers();
//        UserUtils.setViewFields(users);
//        model.addAttribute("users", users);
//        return "admin/admin";
//    }

//    @GetMapping("/show-edit-user")
//    public String showEditUser(@RequestParam long id, ModelMap model) {
//        User user = userService.getUserById(id);
//        UserUtils.setAdminField(user);
//        pw = user.getPassword();
//        user.setPassword("");
//        model.addAttribute("aRoles", UserUtils.allRolesWithoutAdmin());
//        model.addAttribute("user", user);
//        model.addAttribute("title", "Страница администратора");
//        model.addAttribute("title2", "Редактирование пользователя");
//        return "admin/admin-edit";
//    }

//    @GetMapping("/show-repeat-edit-user")
//    public String showRepeatEditUser(ModelMap model) {
//        model.addAttribute("aRoles", UserUtils.allRolesWithoutAdmin());
//        model.addAttribute("user", userRepeatEdit);
//        model.addAttribute("message", message.toString());
//        model.addAttribute("title", "Страница администратора");
//        model.addAttribute("title2", "Редактирование пользователя");
//        return "admin/admin-edit";
//    }

    @PutMapping("/edit-user")
    public String updateUser(@ModelAttribute("user") User user) {
        message = new StringBuilder();
        long idFromForm = user.getId();
        String emailFromForm = user.getEmail();
        User userFromDb = userService.getUserByEmail(emailFromForm);
        boolean emailError = userFromDb != null && idFromForm != userFromDb.getId();
        if (emailError) {
            message.append(user.getEmail()).append(" уже зарегистрирован. Используйте другой е-мэйл.");
        }
        if (!message.isEmpty()) {
            userRepeatEdit = user;
            return "redirect:/admin/show-repeat-edit-user";
        }
        user.setPassword(user.getPassword().isEmpty() ? pw : passwordEncoder.encode(user.getPassword()));
        if (user.isAdmin()) {
            user.getRoles().add(new Role("ADMIN"));
        }
        userService.updateUser(user);
        userRepeatEdit = null;
        pw = null;
        return "redirect:/admin";
    }

//    @GetMapping("/show-add-user")
//    public String showAddUser(ModelMap model) {
//        model.addAttribute("aRoles", UserUtils.allRolesWithoutAdmin());
//        model.addAttribute("user", new User());
//        model.addAttribute("title", "Страница администратора");
//        model.addAttribute("title2", "Новый пользователь");
//        return "admin/admin-edit";
//    }

    @PostMapping("/save-user")
    public String saveUser(@ModelAttribute("user") User user) {
        message = new StringBuilder();
        String emailFromForm = user.getEmail();
        User userFromDb = userService.getUserByEmail(emailFromForm);
        boolean emailError = userFromDb != null;
        if (emailError) {
            message.append(user.getEmail()).append(" уже зарегистрирован. Используйте другой е-мэйл.");
        }
        if (!message.isEmpty()) {
            userRepeatEdit = user;
            return "redirect:/admin/show-repeat-edit-user";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        userRepeatEdit = null;
        return "redirect:/admin";
    }

    @PutMapping("/change-ban/{id}")
    public String changeUserBan(@PathVariable long id) {
        User user = userService.getUserById(id);
        user.setLocked(!user.isLocked());
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/remove-user/{id}")
    public String removeUser(@PathVariable long id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }
}
