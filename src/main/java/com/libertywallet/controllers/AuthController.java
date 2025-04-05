package com.libertywallet.controllers;

import com.libertywallet.exception.EmailNotFoundException;
import com.libertywallet.models.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.libertywallet.repositories.UserRepository;
import com.libertywallet.security.JwtUtil;
import com.libertywallet.services.UserService;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;



    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String,String> request)  {
        String username = request.get("username");
        String email = request.get("email");
        String password = request.get("password");
        log.info("Attempt to register user: {}", email);
        if(username.isBlank() || email.isBlank() || password.isBlank()
        || username == null || email == null || password == null){
            log.warn("Email:{}",email+",username: {}",username+" or password missing:{}",password);
            throw  new IllegalArgumentException("Email,username or password missing");
        }
        userService.userRegister(email, password, username);
        log.info("User successfully registered: {}",email);
        return ResponseEntity.ok("User is sign up!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String,String> request) {
        String email = request.get("email");
        String password = request.get("password");
        log.info("Attempt to login in");
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new EmailNotFoundException(email));

        if(!passwordEncoder.matches(password,user.getPassword())){
            log.warn("Invalid password",email);
            throw new IllegalArgumentException("Invalid password:"+email);
        }
        String token = jwtUtil.generatedToken(email);
        log.info("User successfully sign in");
        return ResponseEntity.ok(token);
    }
}
