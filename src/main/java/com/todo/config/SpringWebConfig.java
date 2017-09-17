package com.todo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Spring MVC configuration.
 *
 * @author Igor Holiak
 */
@Configuration
@EnableWebMvc
public class SpringWebConfig extends WebMvcConfigurerAdapter {

    /**
     * Enable cors for application.
     *
     * @param registry cors registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }
}