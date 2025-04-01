package com.libertywallet.dto;

import lombok.Data;

@Data
public class FeedBackRequest {
    Long userId;
    Long recommendationId;
    Boolean liked;
    Boolean favorite;
}
