package com.ybe.mp10.domain.payment.model;

import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.InheritanceType.JOINED;

import com.ybe.mp10.domain.cart.model.CartProduct;
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
import jakarta.persistence.OneToMany;
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
public class PaymentProduct extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "payment_product_id")
    private Long id;
    private Long accommodationId;
    private String accommodationName;
    private Long roomOptionId;
    private String roomOptionName;
    private String thumbnailImageUrl;
    private String description;
    private Long paymentAmount;
    @Embedded
    private ReservationInfo reservationInfo;
    @Enumerated(STRING)
    private Transportation transportation;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    @Setter
    private Payment payment;

    @OneToMany(mappedBy = "paymentProduct", fetch = LAZY, cascade = REMOVE)
    private final List<PaymentPrice> paymentPrices = new ArrayList<>();

    public static PaymentProduct toEntity(CartProduct cartProduct) {
        return PaymentProduct.builder()
            .accommodationId(cartProduct.getRoomOption().getAccommodation().getId())
            .accommodationName(cartProduct.getRoomOption().getAccommodation().getName())
            .roomOptionId(cartProduct.getRoomOption().getId())
            .thumbnailImageUrl(cartProduct.getRoomOption().getThumbnailImage())
            .roomOptionName(cartProduct.getRoomOption().getName())
            .description(cartProduct.getRoomOption().getDescription())
            .paymentAmount(cartProduct.getReservationInfo().getStayDuration()
                * cartProduct.getRoomOption().getDefaultPrice())
            .reservationInfo(ReservationInfo.builder()
                .numberOfGuest(cartProduct.getReservationInfo().getNumberOfGuest())
                .reservationStartTime(cartProduct.getReservationInfo().getReservationStartTime())
                .reservationEndTime(cartProduct.getReservationInfo().getReservationEndTime())
                .stayDuration(cartProduct.getReservationInfo().getStayDuration())
                .build())
            .transportation(cartProduct.getTransportation())
            .build();


    }

}
