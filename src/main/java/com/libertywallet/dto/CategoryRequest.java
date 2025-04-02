package com.libertywallet.dto;

import com.libertywallet.models.CategoryType;
import lombok.Data;


@Data
public class CategoryRequest {

    String name;
    CategoryType categoryType;
}
