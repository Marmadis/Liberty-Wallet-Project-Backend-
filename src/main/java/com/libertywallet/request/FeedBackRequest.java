package com.libertywallet.request;

import lombok.Data;

import java.util.UUID;

@Data
public class FeedBackRequest {
    UUID recommendationId;
    Boolean liked;
    Boolean favorite;
}
