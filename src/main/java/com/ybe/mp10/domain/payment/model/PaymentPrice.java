package com.ybe.mp10.domain.payment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.InheritanceType.JOINED;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentPrice {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "payment_price_id")
    private Long id;
    private LocalDate stayDate;
    private Long price;

    @ManyToOne
    @JoinColumn(name = "payment_product_id")
    private PaymentProduct paymentProduct;
}
