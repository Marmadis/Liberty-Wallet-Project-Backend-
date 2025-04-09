package com.libertywallet.mapper;

import com.libertywallet.dto.CategoryDto;
import com.libertywallet.models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);



    CategoryDto toDto(Category category);
    Category toEntity(CategoryDto categoryDto);


}