package com.libertywallet.dto;


import jakarta.persistence.Lob;
import lombok.Data;

@Data
public class RecommendationDto {

    private String category;

    @Lob
    private byte[] image;

    private String text;
}
