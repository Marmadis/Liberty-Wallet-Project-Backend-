package com.libertywallet.dto;



import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionRequest {
    Long categoryId;
    BigDecimal amount;
    String description;
    LocalDate date;
}
