package ru.kata.spring.boot_security.demo.successHandlerChain;

import java.util.Set;

public interface SuccessHandler {
    String getUrl();

    static boolean containsTvAndPhoneManufacturer(Set<String> roles) {
        return roles.contains("TV_MANUFACTURER") && roles.contains("PHONE_MANUFACTURER");
    }
}
