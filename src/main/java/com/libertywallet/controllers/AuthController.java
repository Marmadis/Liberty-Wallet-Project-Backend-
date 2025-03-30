package com.libertywallet.controllers;

import com.libertywallet.models.User;
import jakarta.security.auth.message.AuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.libertywallet.repositories.UserRepository;
import com.libertywallet.security.JwtUtil;
import com.libertywallet.services.UserService;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
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
    public ResponseEntity<String> register(@RequestBody Map<String,String> request) throws AuthException {
        String username = request.get("username");
        String email = request.get("email");
        String password = request.get("password");
        log.info("Attempt to register user: {}", email);
        if(username.isBlank() || email.isBlank() || password.isBlank()
        || username == null || email == null || password == null){
            log.warn("Email,username or password missing");
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"Email,username or password missing");
        }
        userService.userRegister(email, password, username);
        log.info("User successfully registered: {}",email);
        return ResponseEntity.ok("User is sign up!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String,String> request) throws AuthException{
        String email = request.get("email");
        String password = request.get("password");
        log.info("Attempt to login in");
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> {
                    log.warn("User not found!");
                        return new ResponseStatusException(HttpStatus.BAD_REQUEST,"User not found!");
                });

        if(!passwordEncoder.matches(password,user.getPassword())){
            log.warn("Invalid password",email);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid password");
        }
        String token = jwtUtil.generatedToken(email);
        log.info("User successfully sign in");
        return ResponseEntity.ok(token);
    }
}
