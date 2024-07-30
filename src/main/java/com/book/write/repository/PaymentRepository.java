package com.book.write.repository;

import com.book.write.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByOrderUid(String OrderUid);
}
