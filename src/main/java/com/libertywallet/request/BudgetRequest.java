package com.libertywallet.request;


import lombok.Data;
import java.time.LocalDate;

@Data
public class BudgetRequest {
    int current_balance;
    int amountLimit;
    LocalDate start_date;
    LocalDate end_date;
}
