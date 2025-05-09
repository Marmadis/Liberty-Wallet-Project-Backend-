package com.libertywallet.controller;

import com.libertywallet.dto.JwtAuthDto;
import com.libertywallet.dto.RefreshTokenDto;
import com.libertywallet.dto.UserCredentialDto;
import com.libertywallet.exception.AuthenticationException;
import com.libertywallet.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.libertywallet.repository.UserRepository;
import com.libertywallet.service.UserService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;


    @PostMapping("/sign-in")
    public ResponseEntity<JwtAuthDto> signIn(@RequestBody UserCredentialDto userCredentialDto){
        try{
            JwtAuthDto jwtAuthDto = userService.signIn(userCredentialDto);
            return ResponseEntity.ok(jwtAuthDto);
        } catch (AuthenticationException e){
            throw new RuntimeException("Auth failed: "+e.getMessage());
        }
    }

    @PostMapping("/refresh")
    public JwtAuthDto refresh (@RequestBody RefreshTokenDto refreshTokenDto) throws Exception{
        return  userService.refreshToken(refreshTokenDto);
    }

}
