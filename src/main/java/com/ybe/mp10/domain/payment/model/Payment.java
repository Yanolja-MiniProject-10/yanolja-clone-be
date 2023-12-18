package com.ybe.mp10.domain.payment.model;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.InheritanceType.JOINED;

import com.ybe.mp10.domain.orders.model.Order;
import com.ybe.mp10.domain.user.model.User;
import com.ybe.mp10.global.common.BaseTimeEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = JOINED)
public class Payment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "payment_id")
    private Long id;
    private Long paymentAmount; // 합산가격

    private Long cartId;

    private String reservationNumber;

    @Setter
    private Boolean paymentCanceled;
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "payment", fetch = LAZY, cascade = ALL)
    private List<PaymentProduct> paymentProducts = new ArrayList<>();
}
