package com.libertywallet.dto;


import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BudgetDto {
    private Long id;
    private int current_balance;
    private int amountLimit;
    private LocalDate start_date;
    private LocalDate end_date;

}
