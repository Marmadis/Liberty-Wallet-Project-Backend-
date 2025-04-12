package com.libertywallet.dto;


import lombok.Data;

@Data
public class RecommendationDto {

    private Long id;
    private String category;
    private String image;

    private String text;
}
