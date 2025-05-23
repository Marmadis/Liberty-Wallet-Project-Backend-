package com.libertywallet.dto;


import lombok.Data;

import java.util.UUID;

@Data
public class RecommendationDto {

    private UUID id;
    private String image;
    private String title;
    private String text;
}
