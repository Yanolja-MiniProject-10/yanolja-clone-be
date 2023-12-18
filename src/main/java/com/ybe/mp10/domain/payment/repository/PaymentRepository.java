package com.ybe.mp10.domain.payment.repository;

import com.ybe.mp10.domain.accommodation.model.Accommodation;
import com.ybe.mp10.domain.payment.dto.response.PaymentResultAccommodationResponse;
import com.ybe.mp10.domain.payment.dto.response.PaymentResultRoomOptionResponse;
import com.ybe.mp10.domain.payment.model.Payment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("select p from Payment p "
        + "where p.user.id = :userId")
    List<Payment> findByUserId(Long userId);


    List<Payment> findAllByUserIdOrderByCreatedTimeDesc(Long userId);


    @Query("select a "
        + "from Payment p, PaymentProduct pp, Accommodation a "
        + "where p.user.id = :userId "
        + "and p.id = :paymentId "
        + "and p.id = pp.payment.id "
        + "and pp.accommodationId = a.id "
        + "group by a.id")
    List<Accommodation> getPaymentAccommodation(Long userId, Long paymentId);

    @Query(
        "select new com.ybe.mp10.domain.payment.dto.response.PaymentResultRoomOptionResponse("
            + "p.id"
            + ", p.accommodationId "
            + ", p.roomOptionId "
            + ", r.name "
            + ", r.thumbnailImage"
            + ", r.capacity "
            + ", p.paymentAmount "
            + ", p.paymentAmount "
            + ", p.reservationInfo.reservationStartTime "
            + ", p.reservationInfo.reservationEndTime "
            + ", p.reservationInfo.stayDuration "
            + ", p.reservationInfo.numberOfGuest "
            + ", p.transportation ) "
            + "from PaymentProduct p, RoomOption r "
            + "where p.roomOptionId = r.id "
            + "and p.id = :paymentProductId"
    )
    PaymentResultRoomOptionResponse getPaymentProductAsRoomOption(Long paymentProductId);
}
