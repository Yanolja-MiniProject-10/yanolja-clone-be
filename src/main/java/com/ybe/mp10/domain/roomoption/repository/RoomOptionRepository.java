package com.ybe.mp10.domain.roomoption.repository;

import com.ybe.mp10.domain.roomoption.dto.response.RoomOptionResponse;
import com.ybe.mp10.domain.roomoption.model.RoomOption;
import com.ybe.mp10.domain.roomoption.model.RoomOptionImage;
import jakarta.persistence.Convert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RoomOptionRepository extends JpaRepository<RoomOption, Long> {
    @Query(value = "SELECT " +
            "ro.room_option_id AS roomOptionId, " +
            "ro.accommodation_id AS accommodationId, " +
            "ro.name AS name, " +
            "ro.room_option_image AS roomOptionImage, " +
            "ro.capacity AS capacity, " +
            "ro.total_room_count AS totalRoomCount, " +
            "COALESCE(COUNT(CASE WHEN " +
            "c.cart_status = 'PAID' AND cp.is_deleted = false AND " +
            "((cp.reservation_start_time <= :startDateTime AND :startDateTime <= cp.reservation_end_time) OR (cp.reservation_start_time <= :endDateTime AND :endDateTime <= cp.reservation_end_time)) THEN 1 ELSE NULL END), " +
            "0) AS reservedRoomCount, " +
            "ro.description AS description, " +
            "ro.check_in_time AS checkInTime, " +
            "ro.check_out_time AS checkOutTime, " +
            "ro.default_price * DATEDIFF(:endDate, :startDate) + COALESCE(SUM(CASE WHEN " +
            "((p.start_date <= :startDate AND :startDate <= p.end_date) OR (p.start_date <= :endDate AND :endDate <= p.end_date)) THEN p.price * DATEDIFF(LEAST(:endDate, p.end_date), GREATEST(:startDate, p.start_date)) ELSE NULL END" +
            "), 0) AS totalPrice, " +
            "DATEDIFF(:endDateTime, :startDateTime) AS stayDuration " +
            "FROM room_option ro " +
            "LEFT OUTER JOIN cart_product cp ON ro.room_option_id = cp.room_option_id " +
            "LEFT OUTER JOIN cart c ON cp.cart_id = c.cart_id " +
            "LEFT OUTER JOIN price p ON ro.room_option_id = p.room_option_id " +
            "WHERE ro.accommodation_id = :accommodationId " +
            "GROUP BY ro.room_option_id",
            nativeQuery = true)
    List<GetRoomOptionResponse> findRoomOptionResponseByAccommodationIdNativeQuery(@Param("accommodationId") Long accommodationId, @Param("startDateTime") LocalDateTime startDateTime, @Param("endDateTime") LocalDateTime endDateTime, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    public interface GetRoomOptionResponse {
        Long getRoomOptionId();
        Long getAccommodationId();
        String getName();
        String getRoomOptionImage();
        Long getCapacity();
        Long getTotalRoomCount();
        Long getReservedRoomCount();
        String getDescription();
        LocalTime getCheckInTime();
        LocalTime getCheckOutTime();
        Long getTotalPrice();
        Long getStayDuration();


    }
    @Query("SELECT new com.ybe.mp10.domain.roomoption.dto.response.RoomOptionResponse(" +
            "ro.id, " +
            "ro.accommodation.id, " +
            "ro.name, " +
            "ro.roomOptionImage, " +
            "ro.capacity, " +
            "ro.totalRoomCount, " +
            "CAST(COALESCE(COUNT(CASE " +
            "WHEN c.cartStatus = 'PAID' AND  " +
            "((cp.reservationInfo.reservationStartTime <= :startDate AND :startDate <= cp.reservationInfo.reservationEndTime) OR (cp.reservationInfo.reservationStartTime <= :endDate AND :endDate <= cp.reservationInfo.reservationEndTime))" +
            "THEN 1" +
            "ELSE null " +
            "END), 0) as Long),"+
            "ro.description, " +
            "ro.checkInTime, " +
            "ro.checkOutTime," +
            "ro.defaultPrice, " +
            ":startDate, " +
            ":endDate) FROM RoomOption ro " +
            "LEFT OUTER JOIN CartProduct cp ON cp.roomOption.id = ro.id " +
            "LEFT OUTER JOIN Cart c ON cp.cart.id = c.id " +
            "WHERE " +
            "ro.id = :roomOptionId " +
            "GROUP BY ro.id")
    Optional<RoomOptionResponse> findRoomOptionByRoomOptionId(@Param("roomOptionId") Long roomOptionId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

}
