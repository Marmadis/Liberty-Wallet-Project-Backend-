package com.libertywallet.request;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BudgetRequest {
    BigDecimal current_balance;
    int amountLimit;
    LocalDate start_date;
    LocalDate end_date;
}
