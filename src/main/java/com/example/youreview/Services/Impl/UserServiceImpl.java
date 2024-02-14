package com.example.youreview.Services.Impl;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import com.example.youreview.Configurations.Utils.JwtManager;
import com.example.youreview.Configurations.Utils.SecurityUtils;
import com.example.youreview.Models.Dtos.SignedInUser;
import com.example.youreview.Models.Dtos.UserAuthDTO;
//import com.example.youreview.Models.Dtos.UserDTO;
import com.example.youreview.Models.Entites.User;
import com.example.youreview.Repositories.UserRepository;
import com.example.youreview.Services.UserService;

@Service
public class UserServiceImpl implements UserService, UserDetailsService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtManager jwtManager;
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtManager jwtManager){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtManager = jwtManager;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("username not found"));
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()))
        );
    }
    // @Override
    // public void createUser(UserDetails user) {
    //     User tmpUser = (User)user;
    //     tmpUser.setPassword(passwordEncoder.encode(tmpUser.getPassword()));
    //     userRepository.save(tmpUser);
    // }
    // @Override
    // public void updateUser(UserDetails user) {
    //     User tmpUser = (User)user;
    //     tmpUser.setPassword(passwordEncoder.encode(tmpUser.getPassword()));
    //     userRepository.save(tmpUser);
    // }
    // @Override
    // public void deleteUser(String username) {
    //     userRepository.deleteByUsername(username);
    // }
    // @Override
    // public void changePassword(String username, String newPassword) {
    //     userRepository.updatePassword(username, newPassword);
    // }
    // @Override
    // public boolean userExists(String username) {
    //     return userRepository.existsByUsername(username);
    // }
    @Override
    public SignedInUser signUser(UserAuthDTO user) {
        User userJPA = userRepository.findByUsername(user.getUsername()).get();
        if(passwordEncoder.matches(user.getPassword(), "{bcrypt}"+userJPA.getPassword())){
            String token = jwtManager.CreateToken(
                loadUserByUsername(userJPA.getUsername())
            );
            System.out.println(
                "USER DETAILS=>"+SecurityUtils.getSessionUser()
            );
            return new SignedInUser().builder().username(userJPA.getUsername())
                                               .id(userJPA.getId())
                                               .token(token)
                                               .build();
        }else{
            throw new InsufficientAuthenticationException("UnAuthorized");
        }
    }
}
