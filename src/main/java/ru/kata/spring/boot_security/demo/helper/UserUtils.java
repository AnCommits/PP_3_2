package ru.kata.spring.boot_security.demo.helper;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.constants.RolesType;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class UserUtils {
    private final UserService userService;

    public UserUtils(UserService userService) {
        this.userService = userService;
    }

    public static void setUsersAgeAndRoles(List<User> users) {
        users.forEach(UserUtils::setUserAgeAndRoles);
    }

    public static void setUserAgeAndRoles(User user) {
        setUserAgeAndBirthDate(user);
        setUserFirstAndOtherRoles(user);
    }

    public static void setUserAgeAndBirthDate(User user) {
        int age;
        String birthDateAsString;
        if (user.getBirthDate() == null) {
            age = -1;
            birthDateAsString = "";
        } else {
            LocalDate localBirthDate = LocalDate.ofInstant(user.getBirthDate().toInstant(), ZoneId.systemDefault());
            age = Period.between(localBirthDate, LocalDate.now()).getYears();
            birthDateAsString = localBirthDate.toString();
        }
        user.setAge(age);
        user.setBirthDateAsString(birthDateAsString);
    }

    public static void setUserFirstAndOtherRoles(User user) {
        List<Role> roles = new ArrayList<>(user.getRoles());
        roles.sort(Role.roleComparator);
        user.setFirstRole(roles.isEmpty() ? "-" : roles.get(0).getName());
        user.setOtherRoles(roles.isEmpty()
                ? new ArrayList<>()
                : roles.stream().skip(1).map(Role::getName).toList());
    }

    public static List<Role> allRoles() {
        return Arrays.stream(RolesType.values()).map(r -> new Role(r.name())).toList();
    }

    public static List<String> allRolesNames() {
        return Arrays.stream(RolesType.values()).map(Enum::name).toList();
    }

    public static String getRolesLine(User user) {
        StringBuilder rolesLine = new StringBuilder();
        user.getRoles().stream()
                .sorted(Role.roleComparator)
                .map(Role::getName)
                .forEach(r -> rolesLine.append(r).append(", "));
        return rolesLine.isEmpty()
                ? "-"
                : rolesLine.substring(0, rolesLine.length() - 2);
    }

    /**
     * Returns true, if user2 or his creator created user1
     */
    public boolean isAncestor(User user1, User user2) {
        long parentAdminId1 = user1.getParentAdminId();
        if (parentAdminId1 == 0) {
            return false;
        }
        if (parentAdminId1 == user2.getId()) {
            return true;
        }
        User user = userService.getUserById(parentAdminId1);
        if (user == null) {
            return false;
        }
        return isAncestor(user, user2);
    }
}
