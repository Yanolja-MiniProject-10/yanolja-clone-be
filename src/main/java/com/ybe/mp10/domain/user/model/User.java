package com.ybe.mp10.domain.user.model;

import com.ybe.mp10.domain.cart.model.Cart;
import com.ybe.mp10.domain.payment.model.Payment;
import com.ybe.mp10.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.InheritanceType.*;

@Entity
@Getter
@NoArgsConstructor
@Inheritance(strategy = JOINED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @NotNull @Column(unique=true)
    private String email;
    @NotNull
    private String password;
    private String name;
    private Boolean isDeleted;

    //    @OneToMany(mappedBy = "user", fetch = LAZY, cascade = REMOVE)
//    private final List<Reservation> reservations = new ArrayList<>();
    @OneToMany(mappedBy = "user", fetch = LAZY, cascade = REMOVE)
    private final List<Cart> carts = new ArrayList<>();
    @OneToMany(mappedBy = "user", fetch = LAZY, cascade = REMOVE)
    private final List<Payment> payments = new ArrayList<>();

    @Builder
    public User(String email, String password, String name, Boolean isDeleted) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.isDeleted = isDeleted;
    }

    public String updateUser(String name) {
        this.name = name;
        return name;
    }

    public void deleteUser() {
        this.isDeleted = false;
    }

    //User 관련 로직 생성전 임시로 사용하기 위해 만듦(김종훈:2023-11-22)
    public User(Long id) {
        this.id = id;
    }
}
