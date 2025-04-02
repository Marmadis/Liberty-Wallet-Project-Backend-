package com.libertywallet.dto;



import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionRequest {
    BigDecimal amount;
    String description;
    LocalDate date;
}
