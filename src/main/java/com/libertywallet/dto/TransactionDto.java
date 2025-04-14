package com.libertywallet.dto;


import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class TransactionDto {

    private UUID id;
    private int amount;
    private String description;
    private LocalDate date;

}
