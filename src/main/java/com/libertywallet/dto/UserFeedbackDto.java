package com.libertywallet.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UserFeedbackDto {
    private UUID id;
    private UUID userId;
    private UUID recommendationId;
    private boolean liked;
    private boolean favorite;
    private String recommendationText;
    private String recommendationCategory;
}
