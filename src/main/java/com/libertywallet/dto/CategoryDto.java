package com.libertywallet.dto;

import com.libertywallet.entity.CategoryType;
import lombok.Data;

@Data
public class CategoryDto {
    CategoryType type;
    String name;
}
