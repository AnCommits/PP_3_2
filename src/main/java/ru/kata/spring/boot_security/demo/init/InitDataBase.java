package ru.kata.spring.boot_security.demo.init;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class InitDataBase {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public InitDataBase(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Method creates and puts an admin in the table in case the table is empty.
     */
    @PostConstruct
    public void initUsers() {
        if (userService.countUsers() == 0) {
            initAdmin();

            initHead();

            initManufactureBoss();

            initTvManufactureMaster();
            initPhoneManufactureMaster();
            initRepairMaster();

            initTvManufacturer1();
            initTvManufacturer2();

            initPhoneManufacturer1();
            initPhoneManufacturer2();

            initRepairer1();
            initRepairer2();

            initTrainee();
        }
    }

    public void initAdmin() {
        Set<Role> roles = new LinkedHashSet<>();
        roles.add(new Role("ADMIN"));
        roles.add(new Role("USER"));
        User user = new User("Абрам", "Абрамов","1",
                passwordEncoder.encode("1"),
                null, roles, false);
        userService.saveUser(user);
    }

    public void initTrainee() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("USER"));
        User user = new User("Антон", "Антонов", "a",
                passwordEncoder.encode("a"),
                new GregorianCalendar(2000, Calendar.JANUARY, 1), roles, false);
        userService.saveUser(user);
    }

    public void initRepairer1() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("REPAIRER"));
        roles.add(new Role("USER"));
        User user = new User("Борис", "Борисов", "b",
                passwordEncoder.encode("b"),
                new GregorianCalendar(2000, Calendar.JANUARY, 1), roles, false);
        userService.saveUser(user);
    }

    public void initRepairer2() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("REPAIRER"));
        roles.add(new Role("USER"));
        User user = new User("Вася", "Васильев", "v",
                passwordEncoder.encode("v"),
                new GregorianCalendar(2000, Calendar.JANUARY, 1), roles, false);
        userService.saveUser(user);
    }

    public void initTvManufacturer1() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("TV_MANUFACTURER"));
        roles.add(new Role("USER"));
        User user = new User("Григорий", "Григорьев", "g",
                passwordEncoder.encode("g"),
                new GregorianCalendar(2000, Calendar.JANUARY, 1), roles, false);
        userService.saveUser(user);
    }

    public void initTvManufacturer2() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("TV_MANUFACTURER"));
        roles.add(new Role("USER"));
        User user = new User("Дима", "Дмитриев", "d",
                passwordEncoder.encode("d"),
                new GregorianCalendar(2000, Calendar.JANUARY, 1), roles, false);
        userService.saveUser(user);
    }

    public void initPhoneManufacturer1() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("PHONE_MANUFACTURER"));
        roles.add(new Role("USER"));
        User user = new User("Егор", "Егоров", "e",
                passwordEncoder.encode("e"),
                new GregorianCalendar(2000, Calendar.JANUARY, 1), roles, false);
        userService.saveUser(user);
    }

    public void initPhoneManufacturer2() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("PHONE_MANUFACTURER"));
        roles.add(new Role("USER"));
        User user = new User("Зина", "Зиновьева", "z",
                passwordEncoder.encode("z"),
                new GregorianCalendar(2000, Calendar.JANUARY, 1), roles, false);
        userService.saveUser(user);
    }

    public void initTvManufactureMaster() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("MASTER"));
        roles.add(new Role("TV_MANUFACTURER"));
        roles.add(new Role("USER"));
        User user = new User("Кирилл", "Кириллов", "k",
                passwordEncoder.encode("k"),
                new GregorianCalendar(2000, Calendar.JANUARY, 1), roles, false);
        userService.saveUser(user);
    }

    public void initPhoneManufactureMaster() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("MASTER"));
        roles.add(new Role("PHONE_MANUFACTURER"));
        roles.add(new Role("USER"));
        User user = new User("Миша", "Михайлов", "m",
                passwordEncoder.encode("m"),
                new GregorianCalendar(2000, Calendar.JANUARY, 1), roles, false);
        userService.saveUser(user);
    }

    public void initRepairMaster() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("MASTER"));
        roles.add(new Role("REPAIRER"));
        roles.add(new Role("USER"));
        User user = new User("Петр", "Петров", "p",
                passwordEncoder.encode("p"),
                new GregorianCalendar(2000, Calendar.JANUARY, 1), roles, false);
        userService.saveUser(user);
    }

    public void initManufactureBoss() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("MASTER"));
        roles.add(new Role("TV_MANUFACTURER"));
        roles.add(new Role("PHONE_MANUFACTURER"));
        roles.add(new Role("USER"));
        User user = new User("Рома", "Романов", "r",
                passwordEncoder.encode("r"),
                new GregorianCalendar(2000, Calendar.JANUARY, 1), roles, false);
        userService.saveUser(user);
    }

    public void initHead() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("MASTER"));
        roles.add(new Role("TV_MANUFACTURER"));
        roles.add(new Role("PHONE_MANUFACTURER"));
        roles.add(new Role("REPAIRER"));
        roles.add(new Role("USER"));
        User user = new User("Степан", "Степанов", "s",
                passwordEncoder.encode("s"),
                new GregorianCalendar(2000, Calendar.JANUARY, 1), roles, false);
        userService.saveUser(user);
    }
}
