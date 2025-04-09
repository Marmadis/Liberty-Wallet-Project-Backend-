package com.libertywallet.request;

import com.libertywallet.entity.CategoryType;
import lombok.Data;


@Data
public class CategoryRequest {
    String name;
    CategoryType categoryType;
}
