package com.libertywallet.controllers;

import com.libertywallet.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.libertywallet.repositories.UserRepository;
import com.libertywallet.security.JwtUtil;
import com.libertywallet.services.UserService;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    @Autowired
    public AuthController(UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder,JwtUtil jwtUtil){
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String,String> request) {
        String username = request.get("username");
        String email = request.get("email");
        String password = request.get("password");
        //BadRequestBody стоит ли добавлять?
        userService.userRegister(email, password, username);
        return ResponseEntity.ok("User is sign up!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String,String> request){
        String email = request.get("email");
        String password = request.get("password");

        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));

        if(!passwordEncoder.matches(password,user.getPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
        }
        String token = jwtUtil.generatedToken(email);
        return ResponseEntity.ok(token);
    }
}
