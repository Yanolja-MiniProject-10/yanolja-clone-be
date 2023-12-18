package com.ybe.mp10.domain.cart.model;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.InheritanceType.JOINED;

import com.ybe.mp10.domain.roomoption.model.RoomOption;
import com.ybe.mp10.global.common.BaseTimeEntity;
import com.ybe.mp10.global.common.ReservationInfo;
import com.ybe.mp10.global.common.enums.Transportation;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = JOINED)
@Builder
public class CartProduct extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "cart_product_id")
    private Long id;
    @Embedded
    private ReservationInfo reservationInfo;
    @Builder.Default
    private Boolean isDeleted = false;
    @Enumerated(STRING)
    @Setter
    private Transportation transportation;


    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
    @ManyToOne
    @JoinColumn(name = "room_option_id")
    private RoomOption roomOption;

    public void delete() {
        this.isDeleted = true;
    }
}
