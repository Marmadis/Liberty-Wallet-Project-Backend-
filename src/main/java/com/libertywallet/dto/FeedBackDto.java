package com.libertywallet.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class FeedBackDto {
    UUID recommendationId;
    boolean liked;
    boolean favorite;
}
