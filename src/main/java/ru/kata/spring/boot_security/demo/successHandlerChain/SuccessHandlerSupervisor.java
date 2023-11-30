package ru.kata.spring.boot_security.demo.successHandlerChain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@AllArgsConstructor
@Getter
public class SuccessHandlerSupervisor implements SuccessHandler {

    private Set<String> roles;

    @Override
    public String getUrl() {
        if (roles.contains("TV_MANUFACTURER")) {
            return "/manufacture/tvs/master";
        }
        if (roles.contains("PHONE_MANUFACTURER")) {
            return "/manufacture/phones/master";
        }
        if (roles.contains("REPAIRER")) {
            return "/repair/master";
        }
        return new SuccessHandlerUser(roles).getUrl();
    }
}
