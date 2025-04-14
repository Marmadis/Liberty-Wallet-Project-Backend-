package com.libertywallet.service;


import com.libertywallet.dto.PaymentDto;
import com.libertywallet.dto.TransactionDto;
import com.libertywallet.entity.*;
import com.libertywallet.exception.NotFoundException;
import com.libertywallet.mapper.PaymentMapper;
import com.libertywallet.repository.CategoryRepository;
import com.libertywallet.repository.PaymentRepository;
import com.libertywallet.repository.TransactionRepository;
import com.libertywallet.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionService transactionService;
    private final TransactionRepository transactionRepository;


    public List<PaymentDto> getPayment(UUID userId){
        List<Payment> payments = paymentRepository.findByUserId(userId);
        List<PaymentDto> paymentDtos = payments.stream()
                .map(paymentMapper::toDto)
                .collect(Collectors.toList());
        if(paymentDtos.isEmpty()){
            log.warn("Payments is empty!");
        }

        log.info("Returned payments list was successfully!");
        return paymentDtos;
    }

    public String createPayment(UUID userId,PaymentDto paymentDto){
        Category category = new Category();
        Payment payment = new Payment();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found: "+userId));

        payment.setUser(user);
        payment.setCurrentSum(paymentDto.getGeneralSum());
        payment.setDate(paymentDto.getDate());
        payment.setName(paymentDto.getName());
        payment.setGeneralSum(paymentDto.getGeneralSum());
        payment.setCurrentNumberOfMonths(paymentDto.getCurrentNumberOfMonths());
        payment.setMonthSum(paymentDto.getMonthSum());
        payment.setNumberOfMonths(paymentDto.getNumberOfMonths());

        category.setName(payment.getName());
        category.setType(CategoryType.EXPENSE);

        categoryRepository.save(category);
        paymentRepository.save(payment);
        return "Created payment was successfully!";
    }

    public String updatePayment(UUID paymentId,PaymentDto paymentDto){
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NotFoundException("Payment not found: "+paymentId));

        if(payment.isCompleted()){
            log.info("Unable to update payment. Because it has already been completed");
            return "Unable to update payment. Because it has already been completed";
        }
        if(Objects.equals(payment.getNumberOfMonths(),paymentDto.getNumberOfMonths()+1)){
            payment.setCompleted(true);
            payment.setDate(payment.getDate().plusMonths(1));
            payment.setCurrentSum(payment.getCurrentSum() - paymentDto.getMonthSum());
            payment.setCurrentNumberOfMonths(paymentDto.getCurrentNumberOfMonths());
            createTransaction(payment);
            log.info("Payment was completed");
        }
        else if(paymentDto.getNumberOfMonths() > payment.getCurrentNumberOfMonths()){
          payment.setCurrentSum(payment.getCurrentSum() - paymentDto.getMonthSum());
          payment.setCurrentNumberOfMonths(paymentDto.getCurrentNumberOfMonths());
          createTransaction(payment);
          payment.setDate(payment.getDate().plusMonths(1));
          log.info("Payment updated successfully");
        }
        else if(paymentDto.getNumberOfMonths() < payment.getCurrentNumberOfMonths()){
            payment.setCurrentSum(payment.getCurrentSum() + paymentDto.getMonthSum());
            payment.setCurrentNumberOfMonths(paymentDto.getCurrentNumberOfMonths()-1);
            transactionRepository.deleteByUserId(payment.getUser().getId());
            payment.setDate(payment.getDate().minusMonths(1));
            log.info("Payment canceled update successfully");
        }



        paymentRepository.save(payment);
        log.info("Edited payment was successfully!");
        return "Edited payment was successfully!";
    }

    private void createTransaction(Payment payment){
        TransactionDto transaction = new TransactionDto();
        transaction.setAmount(payment.getMonthSum());
        transaction.setDescription(payment.getName());
        transaction.setDate(payment.getDate());

        transactionService.createTransaction(payment.getUser().getId(),
                payment.getCategory().getId(),transaction);

        log.info("Created new transaction!");
    }
}
