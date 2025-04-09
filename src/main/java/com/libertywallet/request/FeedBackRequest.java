package com.libertywallet.request;

import lombok.Data;

@Data
public class FeedBackRequest {
    Long recommendationId;
    Boolean liked;
    Boolean favorite;
}
