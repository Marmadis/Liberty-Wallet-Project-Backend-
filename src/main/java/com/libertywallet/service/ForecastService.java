package com.libertywallet.service;

import com.libertywallet.dto.ForecastDto;
import com.libertywallet.dto.TransactionDto;
import com.libertywallet.mapper.TransactionMapper;
import com.libertywallet.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import smile.timeseries.ARMA;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;




@Slf4j
@RequiredArgsConstructor
@Service
public class ForecastService {

    private final TransactionRepository transactionRepository;

    private final TransactionRepository transactionsRepository;
    private final TransactionMapper transactionMapper;


    public List<ForecastDto> getAllMonthsStatistics(UUID userId){
        List<ForecastDto> forecastDtos = new ArrayList<>();
        List<Object[]> monthSum = transactionsRepository.getAllMonthSumExpenses(userId);


        for (Object[] row : monthSum) {
            ForecastDto dto = new ForecastDto();

            dto.setDate(((java.time.Instant) row[0]).atZone(java.time.ZoneId.systemDefault()).toLocalDate());
            dto.setSum(((Number) row[1]).intValue());

            forecastDtos.add(dto);
        }

        return  forecastDtos;
    }
    public double forecastNextMonthExpenses(UUID userId) {
        List<Object[]> rawData = transactionRepository.getRawMonthlyExpenses(userId);

        if (rawData.isEmpty()) {
            throw new IllegalStateException("No spending data available for forecasting.");
        }

        double[] expenses = rawData.stream()
                .mapToDouble(row -> ((Number) row[1]).doubleValue())
                .toArray();

        int dataSize = expenses.length;

        // 1 месяц — просто возвращаем как есть
        if (dataSize == 1) {
            log.info("Only 1 month available — returning that value.");
            return expenses[0];
        }

        // 2 месяца — берём среднее
        if (dataSize == 2) {
            log.info("2 months available — using simple average.");
            return (expenses[0] + expenses[1]) / 2;
        }

        // 3 месяца — берём среднее последних 3
        if (dataSize == 3) {
            log.info("3 months available — using average of last 3 months.");
            return (expenses[0] + expenses[1] + expenses[2]) / 3;
        }

        // 4+ месяцев — используем ARMA
        try {
            double[] diffSeries = difference(expenses);

            if (diffSeries.length < 3) {
                log.warn("After differencing, not enough data for ARMA — fallback to last 3-month average.");
                return Arrays.stream(expenses).skip(Math.max(0, dataSize - 3)).average().orElse(expenses[dataSize - 1]);
            }

            ARMA model = ARMA.fit(diffSeries, 1, 1);
            double forecastDiff = model.forecast(1)[0];

            return expenses[expenses.length - 1] + forecastDiff;
        } catch (Exception e) {
            log.error("ARMA forecast failed — fallback to average of last 3 months.", e);
            return Arrays.stream(expenses).skip(Math.max(0, dataSize - 3)).average().orElse(expenses[dataSize - 1]);
        }
    }
    private double[] difference(double[] series) {
        double[] diff = new double[series.length - 1];
        for (int i = 1; i < series.length; i++) {
            diff[i - 1] = series[i] - series[i - 1];
        }
        return diff;
    }

}