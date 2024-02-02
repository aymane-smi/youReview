package com.example.youreview.Configurations.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.example.youreview.Services.Impl.UserServiceImpl;

@EnableWebSecurity
@Configuration
public class securityConfiguration {
    private UserServiceImpl userService;
    private PasswordEncoder passwordEncoder;
    public securityConfiguration(UserServiceImpl userService, PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception{
        return http.csrf(crsf -> crsf.disable()).authorizeHttpRequests(req -> {
            req.requestMatchers("/", "/css/**", "/js/**").permitAll().anyRequest().authenticated();
        }).formLogin(form->
            form.loginPage("/")
            .failureUrl("/?error=true")
            .defaultSuccessUrl("/reviews")
            .permitAll()
        ).logout(logout -> logout.logoutUrl("/logout").permitAll()).build();
    }


    public void configure(AuthenticationManagerBuilder builder) throws Exception{
        builder.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }
}