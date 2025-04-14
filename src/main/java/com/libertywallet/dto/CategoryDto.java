package com.libertywallet.dto;

import com.libertywallet.entity.CategoryType;
import lombok.Data;

import java.util.UUID;

@Data
public class CategoryDto {
    private UUID id;
    private CategoryType type;
    private String name;
}
