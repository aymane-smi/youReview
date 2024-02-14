package com.example.youreview.Configurations.Utils;

//import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {
    public static Object getSessionUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        if(authentication instanceof JwtAuthenticationToken)
            return authentication.getPrincipal();
        return null;
    }
    
}
