package com.libertywallet.dto;

import lombok.Data;

@Data
public class JwtAuthDto {
    private String token;
    private String refreshToken;
}
