package com.libertywallet.mapper;

import com.libertywallet.dto.RecommendationDto;
import com.libertywallet.entity.Recommendation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RecommendationMapper {
    RecommendationMapper INSTANCE = Mappers.getMapper(RecommendationMapper.class);
    RecommendationDto toDto(Recommendation recommendation);
    Recommendation toEntity(RecommendationDto recommendationDto);

}
