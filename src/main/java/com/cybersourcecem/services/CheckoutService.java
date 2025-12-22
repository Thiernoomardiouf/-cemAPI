package com.cybersourcecem.services;

import com.cybersourcecem.entities.Order;
import com.cybersourcecem.entities.Payment;
import com.cybersourcecem.repositories.OrderRepository;
import com.cybersourcecem.repositories.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CheckoutService {

    private final OrderRepository orderRepo;
    private final PaymentRepository paymentRepo;
    private final PaymentService paymentService;

    public CheckoutService(OrderRepository orderRepo, PaymentRepository paymentRepo, PaymentService paymentService) {
        this.orderRepo = orderRepo;
        this.paymentRepo = paymentRepo;
        this.paymentService = paymentService;
    }

    public Map<String, Object> processPayment(Long amountCents, String card, String month, String year, String cvv) {

        // 1) Créer la commande
        Order order = new Order();
        order.setAmountCents(amountCents);
        order.setStatus("PENDING");
        order = orderRepo.save(order);

        // 2) Appel à Cybersource
        String resp = paymentService.createPayment(card, month, year, cvv, amountCents);

        // 3) Déterminer le statut
        String status =
                resp.contains("AUTHORIZED") ? "AUTHORIZED" :
                        resp.contains("CAPTURED")   ? "CAPTURED" :
                                resp.contains("DECLINED")   ? "DECLINED" :
                                        "FAILED";

        // 4) Sauvegarder le paiement
        Payment p = new Payment();
        p.setOrderId(order.getId());
        p.setStatus(status);
        p.setRawResponse(resp);
        // récupérer le requestID Cybersource si présent
        p.setCybersourceRequestId(extract(resp, "requestID"));
        paymentRepo.save(p);

        // 5) Mettre à jour la commande
        order.setStatus(status);
        orderRepo.save(order);

        // 6) Retourner au front
        return Map.of(
                "orderId", order.getId(),
                "status", status,
                "message", status.equals("AUTHORIZED") ?
                        "Paiement autorisé" :
                        (status.equals("CAPTURED") ? "Paiement capturé" : "Paiement refusé"),
                "raw", resp
        );
    }

    private String extract(String text, String key) {
        int i = text.indexOf(key);
        if (i == -1) return null;
        int s = text.indexOf(":", i);
        int e = text.indexOf(",", s);
        return text.substring(s+1, e).replace("\"", "").trim();
    }
}

