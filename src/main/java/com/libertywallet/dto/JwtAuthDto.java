package com.libertywallet.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class JwtAuthDto {
    private UUID userId;
    private String token;
    private String refreshToken;
}
