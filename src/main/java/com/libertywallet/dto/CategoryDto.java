package com.libertywallet.dto;

import com.libertywallet.entity.CategoryType;
import lombok.Data;

@Data
public class CategoryDto {
    private Long id;
    private CategoryType type;
    private String name;
}
