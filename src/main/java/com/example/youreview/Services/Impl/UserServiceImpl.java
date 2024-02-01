package com.example.youreview.Services.Impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import com.example.youreview.Models.Entites.User;
import com.example.youreview.Repositories.UserRepository;
import com.example.youreview.Services.UserService;

@Service
public class UserServiceImpl implements UserService, UserDetailsManager{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("username not found"));
    }
    @Override
    public void createUser(UserDetails user) {
        User tmpUser = (User)user;
        tmpUser.setPassword(passwordEncoder.encode(tmpUser.getPassword()));
        userRepository.save(tmpUser);
    }
    @Override
    public void updateUser(UserDetails user) {
        User tmpUser = (User)user;
        tmpUser.setPassword(passwordEncoder.encode(tmpUser.getPassword()));
        userRepository.save(tmpUser);
    }
    @Override
    public void deleteUser(String username) {
        userRepository.deleteByUsername(username);
    }
    @Override
    public void changePassword(String username, String newPassword) {
        userRepository.updatePassword(username, newPassword);
    }
    @Override
    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }
}
