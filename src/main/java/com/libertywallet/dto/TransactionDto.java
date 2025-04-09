package com.libertywallet.dto;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionDto {


    private int amount;
    private String description;
    private LocalDate date;

}
