package com.libertywallet.controller;


import com.libertywallet.dto.PaymentDto;
import com.libertywallet.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/get/{userId}")
    public ResponseEntity<List<PaymentDto>> getPayment(@PathVariable UUID userId){
        return ResponseEntity.ok(paymentService.getPayment(userId));
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<String> createPayment(@PathVariable UUID userId,@RequestBody PaymentDto paymentDto){
        return ResponseEntity.ok(paymentService.createPayment(userId,paymentDto));
    }

    @PostMapping("/update/{userId}")
    public ResponseEntity<String> updatePayment(@PathVariable UUID userId,@RequestBody PaymentDto paymentDto){
        return ResponseEntity.ok(paymentService.updatePayment(userId,paymentDto));
    }

}
