package ru.kata.spring.boot_security.demo.successHandlerChain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@AllArgsConstructor
@Getter
public class SuccessHandlerUser implements SuccessHandler {

    private Set<String> roles;

    @Override
    public String getUrl() {
        return roles.contains("USER") ? "/user" : "/";
    }
}
