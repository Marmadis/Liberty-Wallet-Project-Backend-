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

            dto.setDate(((java.sql.Timestamp) row[0]).toLocalDateTime().toLocalDate());
            dto.setSum(((Number) row[1]).intValue());

            forecastDtos.add(dto);
        }

        return  forecastDtos;
    }

    public double forecastNextMonthExpenses(UUID userId) {
        List<Object[]> rawData = transactionRepository.getRawMonthlyExpenses(userId);

        if (rawData.isEmpty()) {
            throw new IllegalStateException("No spending data");
        } else if (rawData.size() == 1) {
            log.info("Due to insufficient information, we are returning only the first month.");
            return ((Number) rawData.get(0)[1]).doubleValue();
        } else if (rawData.size() == 2) {
            log.info("Due to insufficient information, we return the average value of the last two months.");
            double month1 = ((Number) rawData.get(0)[1]).doubleValue();
            double month2 = ((Number) rawData.get(1)[1]).doubleValue();
            return (month1 + month2) / 2;
        }


        double[] expenses = rawData.stream()
                .mapToDouble(row -> ((Number) row[1]).doubleValue())
                .toArray();

        try {
            // Преобразуем в стационарный ряд (разностное преобразование)
            double[] diffSeries = difference(expenses);

            // Обучаем модель ARMA(1,1)
            ARMA model = ARMA.fit(diffSeries, 1, 1);

            // Прогнозируем следующий элемент
            double forecastDiff = model.forecast(1)[0];

            // Возвращаем к исходному масштабу
            return expenses[expenses.length - 1] + forecastDiff;
        } catch (Exception e) {
            throw new RuntimeException("ARMA forecast error", e);
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