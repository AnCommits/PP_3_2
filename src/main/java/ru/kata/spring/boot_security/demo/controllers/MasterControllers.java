package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.helper.UserUtils;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MasterControllers {
    private final UserService userService;

    public MasterControllers(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/repair/master")
    public String showRepairs(ModelMap model) {
        Role r0 = new Role("REPAIRER");
        List<Role> roles = new ArrayList<>(List.of(r0));
        List<User> users = userService.getUsersByRoles(roles);
        UserUtils.setViewFields(users);
        model.addAttribute("users", users);
        return "repair/master/repair-master";
    }

    @GetMapping("/manufacture/tvs/master")
    public String showTvManufacturers(ModelMap model) {
        Role r1 = new Role("TV_MANUFACTURER");
        List<Role> roles = new ArrayList<>(List.of(r1));
        List<User> users = userService.getUsersByRoles(roles);
        UserUtils.setViewFields(users);
        model.addAttribute("users", users);
        return "manufacture/tvs/master/tv-manufacture-master";
    }

    @GetMapping("/manufacture/phones/master")
    public String showPhonesManufacturers(ModelMap model) {
        Role r2 = new Role("PHONE_MANUFACTURER");
        List<Role> roles = new ArrayList<>(List.of(r2));
        List<User> users = userService.getUsersByRoles(roles);
        UserUtils.setViewFields(users);
        model.addAttribute("users", users);
        return "manufacture/phones/master/phones-manufacture-master";
    }

    @GetMapping("/manufacture/master")
    public String showManufacturers(ModelMap model) {
        Role r1 = new Role("TV_MANUFACTURER");
        Role r2 = new Role("PHONE_MANUFACTURER");
        List<Role> roles = new ArrayList<>(List.of(r1, r2));
        List<User> users = userService.getUsersByRoles(roles);
        UserUtils.setViewFields(users);
        model.addAttribute("users", users);
        return "manufacture/master/manufacture-master";
    }

    @GetMapping("/headship")
    public String showHeadship(ModelMap model) {
        Role r1 = new Role("TV_MANUFACTURER");
        Role r2 = new Role("PHONE_MANUFACTURER");
        Role r3 = new Role("REPAIRER");
        List<Role> roles = new ArrayList<>(List.of(r1, r2, r3));
        List<User> users = userService.getUsersByRoles(roles);
        UserUtils.setViewFields(users);
        model.addAttribute("users", users);
        return "headship/headship";
    }
}
