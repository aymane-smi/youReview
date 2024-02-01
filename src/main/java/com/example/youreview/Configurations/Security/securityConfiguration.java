package com.example.youreview.Configurations.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class securityConfiguration {
    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception{
        return http.csrf(crsf -> crsf.disable()).authorizeHttpRequests(req -> {
            req.requestMatchers("/", "/css/**", "/js/**").permitAll();
        }).formLogin(form->
            form.loginPage("/")
            .loginProcessingUrl("/")
            .failureUrl("/?error=true")
            .permitAll()
        ).logout(logout -> logout.logoutUrl("/logout").permitAll()).build();
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
