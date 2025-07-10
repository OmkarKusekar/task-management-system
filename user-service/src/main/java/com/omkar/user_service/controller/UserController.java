package com.omkar.user_service.controller;

import com.omkar.user_service.model.User;
import com.omkar.user_service.repository.UserRepository;
import com.omkar.user_service.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private  UserService userService;


    @PostMapping("/register")
    public ResponseEntity<User>register(@RequestBody User user){
        return ResponseEntity.ok(userService.registerUser(user));
    }
    public ResponseEntity<String >login(@RequestBody User user){
        String token= userService.loginUser(user.getUsername(), user.getPassword());
        return ResponseEntity.ok(token);
    }

}
