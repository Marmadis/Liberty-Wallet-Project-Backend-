package com.libertywallet.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ForecastDto {
    private int sum;
    private LocalDate date;
}
