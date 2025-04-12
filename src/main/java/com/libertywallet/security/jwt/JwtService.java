package com.libertywallet.security.jwt;


import com.libertywallet.dto.JwtAuthDto;
import com.libertywallet.entity.User;
import com.libertywallet.exception.NotFoundException;
import com.libertywallet.exception.SecurityJwtException;
import com.libertywallet.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtService {

    @Value("9435af0efe6f69d8eb9ca5633557af9112ca7f5a408239cd85d38d1482a5d5309672e959b036039c02992740ad2b62245748f050b4d9af36fa494eeee04eb949e42fa5215ad800a78de2637d89f24c7651346c091d4b6cb70b09cce450ac11058849fc2b5f052e45121ccc78ba71fa6e9e16b4923a4b6bae3a0f7cfdc757f5fdecd68c244d7e9b099539e050e01cd914f57e618fde45ae3e13f4b689cf9499c6139de5b2a61b8357a307eb8fc7d42188d02228a112316e2d83e1a8e987df45168111968472dbfcc68daf8e7eed56ff955d269eb26b1630d20c2d3487be3a1ca2fce5e2ecc449004e89f4c1661b7f820a449b2b04e1d89f227d64b4ccd867c73b")
    private String jwtSecret;
    private final UserRepository userRepository;

    public JwtAuthDto generateToken(String email){
        JwtAuthDto jwtDto = new JwtAuthDto();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found by email:"+email));
        jwtDto.setUserId(user.getId());
        jwtDto.setToken(generateJwtToken(email));
        jwtDto.setRefreshToken(generateRefreshToken(email));
        return  jwtDto;
    }

    public JwtAuthDto refreshBaseToken(String email,String refreshToken){
        JwtAuthDto jwtDto = new JwtAuthDto();
        jwtDto.setToken(generateJwtToken(email));
        jwtDto.setRefreshToken(refreshToken);
        return jwtDto;
    }

    public String getEmailFromToken(String token){
        Claims claims = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    public boolean validateJwtToken(String token){
        try{
            Jwts.parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
        } catch (ExpiredJwtException e){
            throw new SecurityJwtException("Expired JwtExcpetion: "+e.getMessage());
        } catch (UnsupportedJwtException e){
            throw new SecurityJwtException("Unsupported JwtExcpetion: "+e.getMessage());
        }catch (MalformedJwtException e){
            throw new SecurityJwtException("Malformed JwtExcpetion: "+e.getMessage());
        }catch (SecurityException e){
            throw new SecurityJwtException("Security JwtExcpetion: "+e.getMessage());
        }catch (Exception e){
            throw new SecurityJwtException("Invalid token: "+e.getMessage());
        }
    }

    private String generateJwtToken(String email){
        Date date = Date.from(LocalDateTime.now().plusHours(1).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .subject(email)
                .expiration(date)
                .signWith(getSignInKey())
                .compact();

    }

    private String generateRefreshToken(String email){
        Date date = Date.from(LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .subject(email)
                .expiration(date)
                .signWith(getSignInKey())
                .compact();
    }

    private SecretKey getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
