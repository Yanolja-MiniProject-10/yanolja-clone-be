package com.ybe.mp10.domain.payment.repository;

import com.ybe.mp10.domain.payment.model.PaymentProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentProductRepository extends JpaRepository<PaymentProduct, Long> {

}
