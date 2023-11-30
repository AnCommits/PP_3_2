package ru.kata.spring.boot_security.demo.helper;

import ru.kata.spring.boot_security.demo.constants.RolesType;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserUtils {

    public static void setViewFields(List<User> users) {
        users.forEach(UserUtils::setAllAdditionalFields);
    }

    public static void setAllAdditionalFields(User user) {
        setDatesFields(user);
        setRoleFields(user);
        setAdminField(user);
    }

    public static void setDatesFields(User user) {
        user.setBirthDateAsString(user.getBirthDate() != null
                ? new SimpleDateFormat("yyyy-MM-dd").format(user.getBirthDate().getTime())
                : "");
        user.setRecordDateTimeAsString(user.getRecordDateTime() != null
                ? new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss").format(user.getRecordDateTime())
                : "");
    }

    public static void setRoleFields(User user) {
        List<Role> roles = new ArrayList<>(user.getRoles());
        roles.sort(Role.roleComparator);
        user.setFirstRole(roles.isEmpty() ? "-" : roles.get(0).getName());
        user.setOtherRoles(roles.isEmpty()
                ? new ArrayList<>()
                : roles.stream().skip(1).map(Role::getName).toList());
    }

    public static void setAdminField(User user) {
        user.setAdmin(user.getRoles().stream().anyMatch(r -> r.getName().equals("ADMIN")));
    }

    public static List<Role> allRolesWithoutAdmin() {
        return Arrays.stream(RolesType.values())
                .filter(r -> !r.name().equals("ADMIN"))
                .map(r -> new Role(r.name())).toList();
    }
}
