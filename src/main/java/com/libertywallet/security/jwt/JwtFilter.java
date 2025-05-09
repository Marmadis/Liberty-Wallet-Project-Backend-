package com.libertywallet.security.jwt;

import com.libertywallet.security.CustomUserDetails;
import com.libertywallet.security.CustomUserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserServiceImpl customUserService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

      String token = getTokenFromRequest(request);
      if(token != null && jwtService.validateJwtToken(token)){
          setCustomUserDetailsToSecurityContextHolders(token);
      }
      filterChain.doFilter(request,response);
    }

    private void   setCustomUserDetailsToSecurityContextHolders(String token){
        String email = jwtService.getEmailFromToken(token);
        CustomUserDetails customUserDetails = customUserService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(customUserDetails,
                null,
                customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }

    private String getTokenFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }
}
