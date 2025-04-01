package com.libertywallet.dto;


import jakarta.persistence.Lob;
import lombok.Data;

@Data
public class RecommendationDTO {

    private String category;

    @Lob
    private byte[] image;

    private String text;
}
