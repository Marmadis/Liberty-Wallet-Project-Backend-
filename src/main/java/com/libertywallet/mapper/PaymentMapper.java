package com.libertywallet.mapper;


import com.libertywallet.dto.PaymentDto;
import com.libertywallet.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);
    PaymentDto toDto(Payment payment);
    Payment toEntity(PaymentDto paymentDto);

}
