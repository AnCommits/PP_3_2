package ru.kata.spring.boot_security.demo.helper;

import ru.kata.spring.boot_security.demo.constants.RolesType;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserUtils {

    public static void setUsersAgeAndRoles(List<User> users) {
        users.forEach(UserUtils::setUserAgeAndRoles);
    }

    public static void setUserAgeAndRoles(User user) {
        setUserAgeAndBirthDate(user);
        setUserFirstAndOtherRoles(user);
    }

//    public static void setAllAdditionalFields(User user) {
//        setUserAgeAndBirthDate(user);
//        setUserFirstAndOtherRoles(user);
//        setAdminField(user);
//    }

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

//    public static void setAdminField(User user) {
//        user.setAdmin(user.getRoles().stream().anyMatch(r -> r.getName().equals("ADMIN")));
//    }

    public static List<Role> allRoles() {
        return Arrays.stream(RolesType.values()).map(r -> new Role(r.name())).toList();
    }

    public static List<String> allRolesNames() {
        return Arrays.stream(RolesType.values()).map(Enum::name).toList();
    }

    // todo delete

//    public static List<Role> allRolesWithoutAdmin() {
//        return Arrays.stream(RolesType.values())
//                .filter(r -> !r.name().equals("ADMIN"))
//                .map(r -> new Role(r.name())).toList();
//    }

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
}
