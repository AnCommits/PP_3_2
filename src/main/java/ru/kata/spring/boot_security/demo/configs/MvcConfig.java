package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/home").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/error").setViewName("error");

        registry.addViewController("/manufacture/tvs")
                .setViewName("manufacture/tvs/tvs-manufacture-page");

        registry.addViewController("/manufacture/phones")
                .setViewName("manufacture/phones/phones-manufacture-page");

        registry.addViewController("/manufacture")
                .setViewName("manufacture/manufacture-page");

        registry.addViewController("/repair").setViewName("repair/repair-page");
    }
}
