package com.cybersourcecem.web;

import com.cybersourcecem.services.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
//@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService svc;

    public PaymentController(PaymentService svc) { this.svc = svc; }

    //@PostMapping("/charge")
    public ResponseEntity<String> charge(@RequestParam String cardNumber,
                                         @RequestParam String expMonth,
                                         @RequestParam String expYear,
                                         @RequestParam String cvv,
                                         @RequestParam Long amountCents) {
        String resp = svc.createPayment(cardNumber, expMonth, expYear, cvv, amountCents);
        return ResponseEntity.ok(resp);
    }
}
