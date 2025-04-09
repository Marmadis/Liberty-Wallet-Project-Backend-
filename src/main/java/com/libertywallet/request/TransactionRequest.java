package com.libertywallet.request;



import lombok.Data;
import java.time.LocalDate;

@Data
public class TransactionRequest {
    Long categoryId;
    int amount;
    String description;
    LocalDate date;
}
