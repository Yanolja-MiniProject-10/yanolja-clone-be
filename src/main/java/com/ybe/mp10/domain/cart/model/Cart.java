package com.ybe.mp10.domain.cart.model;

import com.ybe.mp10.domain.user.model.User;
import com.ybe.mp10.global.common.BaseTimeEntity;
import com.ybe.mp10.global.common.enums.CartStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.InheritanceType.JOINED;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = JOINED)
@Builder
public class Cart extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "cart_id")
    private Long id;
    @Enumerated(STRING)
    @Setter
    private CartStatus cartStatus;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "cart", fetch = LAZY, cascade = REMOVE)
    private final List<CartProduct> cartProducts = new ArrayList<>();


}
