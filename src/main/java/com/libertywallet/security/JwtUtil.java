package com.libertywallet.security;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    //РЕШИТЬ ПРОБЛЕМУ С КЛЮЧОМ
    private final Key secretKey = Keys.hmacShaKeyFor("I_sucked_and_they_fucked_me_very_long_super_secret_key".getBytes());//Временно

    public String generatedToken(String email){
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) //Пока что 1 час
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();

    }
}
