package com.example.youreview.Configurations.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.youreview.Services.Impl.UserServiceImpl;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class securityConfiguration {

    private final UserServiceImpl userService;
    private final JwtAuthenticationFilter jwtFilter;

    public securityConfiguration(UserServiceImpl userService, JwtAuthenticationFilter jwtFilter){
        this.userService = userService;
        this.jwtFilter = jwtFilter;
    }
    @Bean
     protected SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
        http.formLogin(login->{
            login.disable();
        }).csrf(crsf ->{
            crsf.disable();
        }).cors(cors -> {
            cors.disable();
        }).authorizeHttpRequests(req -> {
                req.requestMatchers("/api/auth/signin").permitAll().anyRequest().authenticated();
            })
            .sessionManagement(session -> {
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            }).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
}