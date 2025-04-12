package com.libertywallet.dto;


import lombok.Data;
import java.time.LocalDate;

@Data
public class TransactionDto {

    private Long id;
    private int amount;
    private String description;
    private LocalDate date;

}
