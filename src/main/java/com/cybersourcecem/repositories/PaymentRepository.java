package com.cybersourcecem.repositories;

import com.cybersourcecem.entities.Payment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//@Transactional
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
