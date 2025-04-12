package com.libertywallet.mapper;

import com.libertywallet.dto.TransactionDto;
import com.libertywallet.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);
    TransactionDto toDto(Transaction transaction);
    Transaction toEntity(TransactionDto transactionDto);
}
