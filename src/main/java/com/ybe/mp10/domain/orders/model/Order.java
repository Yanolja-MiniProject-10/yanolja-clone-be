package com.ybe.mp10.domain.orders.model;

import com.ybe.mp10.domain.payment.model.Payment;
import com.ybe.mp10.domain.user.model.User;
import com.ybe.mp10.global.common.BaseTimeEntity;
import com.ybe.mp10.global.common.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.InheritanceType.JOINED;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = JOINED)
@Builder
@Table(name = "orders")
public class Order extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    private String orderNumber;
    @Enumerated(STRING)
    private OrderStatus orderStatus;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "order", fetch = LAZY, cascade = REMOVE)
    private final List<OrderProduct> orderProducts = new ArrayList<>();
    @OneToOne(mappedBy = "order")
    private Payment payment;
}
