package com.libertywallet.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class PaymentDto {

    private Long id;

    private String name;

    private LocalDate date;

    private int monthSum;

    private int numberOfMonths;

    private int currentNumberOfMonths;

    private int currentSum;

    private int generalSum;

    private boolean completed;

}
