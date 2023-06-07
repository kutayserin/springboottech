package com.kutay.springboottech.dao;

import com.kutay.springboottech.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {

    Payment findByUserEmail(String userEmail);

}
