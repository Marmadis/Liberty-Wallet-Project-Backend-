package com.libertywallet.dto;


import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class BudgetDto {
    private UUID id;
    private boolean amountLimitWarn;
    private int current_balance;
    private int amountLimit;
    private LocalDate start_date;
    private LocalDate end_date;

}
