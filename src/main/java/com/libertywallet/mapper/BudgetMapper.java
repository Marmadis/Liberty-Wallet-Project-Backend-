package com.libertywallet.mapper;


import com.libertywallet.dto.BudgetDto;
import com.libertywallet.dto.RecommendationDto;
import com.libertywallet.entity.Budget;
import com.libertywallet.entity.Recommendation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BudgetMapper {

    BudgetMapper INSTANCE = Mappers.getMapper(BudgetMapper.class);

    BudgetDto toDto(Budget budget);
    Budget toEntity(BudgetDto budgetDto);

}
