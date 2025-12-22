package com.cybersourcecem.web;

import com.cybersourcecem.services.CheckoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
//@RequestMapping("/api/checkout")
@RequestMapping("/api/payments")
public class CheckoutController {

    private final CheckoutService service;

    public CheckoutController(CheckoutService service) {
        this.service = service;
    }

   // @PostMapping("/pay")
    @PostMapping("/charge")
    public ResponseEntity<Map<String, Object>> pay(
            @RequestParam Long amountCents,
            @RequestParam String cardNumber,
            @RequestParam String expMonth,
            @RequestParam String expYear,
            @RequestParam String cvv
    ) {
        return ResponseEntity.ok(
                service.processPayment(amountCents, cardNumber, expMonth, expYear, cvv)
        );
    }
}

