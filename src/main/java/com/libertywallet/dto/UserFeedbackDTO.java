package com.libertywallet.dto;

import lombok.Data;

@Data
public class UserFeedbackDTO {
    private Long id;
    private Long userId;
    private Long recommendationId;
    private boolean liked;
    private boolean favorite;
    private String recommendationText;
    private String recommendationCategory;
}
