package com.omkar.user_service.service;

import com.omkar.user_service.model.User;
import com.omkar.user_service.repository.UserRepository;
import com.omkar.user_service.security.JwtUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  JwtUtil jwtUtil;

    public User registerUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    public String loginUser(String username,String password){
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new RuntimeException("User Not Found"));
        if (passwordEncoder.matches(password,user.getPassword())){
            return jwtUtil.generateToken(user.getUsername());
        }else{
            throw new RuntimeException("Invalid credentials");
        }
    }

}
