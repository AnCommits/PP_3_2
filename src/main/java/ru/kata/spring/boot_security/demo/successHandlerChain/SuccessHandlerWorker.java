package ru.kata.spring.boot_security.demo.successHandlerChain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@AllArgsConstructor
@Getter
public class SuccessHandlerWorker implements SuccessHandler {

    private Set<String> roles;

    @Override
    public String getUrl() {
        if (roles.contains("TV_MANUFACTURER")) {
            return "/manufacture/tvs";
        }
        if (roles.contains("PHONE_MANUFACTURER")) {
            return "/manufacture/phones";
        }
        if (roles.contains("REPAIRER")) {
            return "/repair";
        }
        return new SuccessHandlerUser(roles).getUrl();
    }
}
