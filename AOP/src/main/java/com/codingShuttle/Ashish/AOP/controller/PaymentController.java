package com.codingShuttle.Ashish.AOP.controller;

import com.codingShuttle.Ashish.AOP.service.PaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/pay")
    public void pay() {
        paymentService.makePayment();
    }
}
