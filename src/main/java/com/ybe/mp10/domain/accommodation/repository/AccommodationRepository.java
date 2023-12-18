package com.ybe.mp10.domain.accommodation.repository;

import com.ybe.mp10.domain.accommodation.dto.response.AccommodationResponse;
import com.ybe.mp10.domain.accommodation.model.Accommodation;
import com.ybe.mp10.domain.category.model.Category;
import com.ybe.mp10.domain.region.model.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    @Query(value = "SELECT NEW com.ybe.mp10.domain.accommodation.dto.response.AccommodationResponse(" +
            "a.id, " +
            "a.name, " +
            "a.thumbnailImageUrl, " +
            "a.category, " +
            "a.region, " +
            "CAST(COALESCE(MIN(ro.defaultPrice), 0) as Long) , " +
            "CAST(COALESCE(MAX(ro.defaultPrice), 500000) as Long), " +
            "CAST(SUM(DISTINCT CASE WHEN ((p.startDate <= :startDate AND :startDate <= p.endDate) OR (p.startDate <= :endDate AND :endDate <= p.endDate)) THEN p.price ELSE 0 END) AS LONG)" +
            ") FROM Accommodation a " +
            "LEFT OUTER JOIN RoomOption ro ON a.id = ro.accommodation.id " +
            "LEFT OUTER JOIN CartProduct cp ON ro.id = cp.roomOption.id  " +
            "LEFT OUTER JOIN Cart c ON cp.cart.id = c.id " +
            "LEFT OUTER JOIN Price p ON ro.id = p.roomOption.id " +
            "WHERE " +
            "a.name LIKE concat('%',:name, '%') " +
            "AND a.region = :region " +
            "AND ro.capacity >= :guest " +
            "GROUP BY a.id HAVING COALESCE(SUM(ro.totalRoomCount), 0) > COALESCE(COUNT(CASE " +
            "WHEN c.cartStatus = 'PAID' AND  " +
            "((cp.reservationInfo.reservationStartTime <= :startDateTime AND :startDateTime <= cp.reservationInfo.reservationEndTime) OR (cp.reservationInfo.reservationStartTime <= :endDateTime AND :endDateTime <= cp.reservationInfo.reservationEndTime)) AND cp.isDeleted = false " +
            "THEN 1" +
            "ELSE null " +
            "END), -1) " +
            "ORDER BY CAST(COALESCE(MIN(ro.defaultPrice), 0) as Long) ASC",
            countQuery =
                    "SELECT COUNT(a) FROM Accommodation a " +
                            "LEFT OUTER JOIN RoomOption ro ON a.id = ro.accommodation.id " +
                            "LEFT OUTER JOIN CartProduct cp ON ro.id = cp.roomOption.id  " +
                            "LEFT OUTER JOIN Cart c ON cp.cart.id = c.id " +
                            "LEFT OUTER JOIN Price p ON ro.id = p.roomOption.id " +
                            "WHERE " +
                            "a.name = :name " +
                            "AND a.region = :region " +
                            "AND ro.capacity >= :guest " +
                            "AND :startDate <= :endDate " +
                            "GROUP BY a.id HAVING COALESCE(SUM(ro.totalRoomCount), 0) > COALESCE(COUNT(CASE " +
                            "WHEN c.cartStatus = 'PAID' AND  " +
                            "((cp.reservationInfo.reservationStartTime <= :startDateTime AND :startDateTime <= cp.reservationInfo.reservationEndTime) OR (cp.reservationInfo.reservationStartTime <= :endDateTime AND :endDateTime <= cp.reservationInfo.reservationEndTime)) AND cp.isDeleted = false " +
                            "THEN 1" +
                            "ELSE null " +
                            "END), -1)"
    )
    Page<AccommodationResponse> getAccommodationsByNameAndRegion(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime,
            @Param("guest") Long guest,
            @Param("name") String name,
            @Param("region") Region region,
            Pageable pageable);

    @Query(value = "SELECT NEW com.ybe.mp10.domain.accommodation.dto.response.AccommodationResponse(" +
            "a.id, " +
            "a.name, " +
            "a.thumbnailImageUrl, " +
            "a.category, " +
            "a.region, " +
            "CAST(COALESCE(MIN(ro.defaultPrice), 0) as Long) , " +
            "CAST(COALESCE(MAX(ro.defaultPrice), 500000) as Long), " +
            "CAST(SUM(DISTINCT CASE WHEN ((p.startDate <= :startDate AND :startDate <= p.endDate) OR (p.startDate <= :endDate AND :endDate <= p.endDate)) THEN p.price ELSE 0 END) AS LONG)" +
            ") FROM Accommodation a " +
            "LEFT OUTER JOIN RoomOption ro ON a.id = ro.accommodation.id " +
            "LEFT OUTER JOIN CartProduct cp ON cp.roomOption.id = ro.id " +
            "LEFT OUTER JOIN Cart c ON cp.cart.id = c.id " +
            "LEFT OUTER JOIN Price p ON ro.id = p.roomOption.id " +
            "WHERE " +
            "ro.capacity >= :guest " +
            "GROUP BY a.id HAVING COALESCE(SUM(ro.totalRoomCount), 0) > COALESCE(COUNT(case " +
            "when c.cartStatus = 'PAID' AND  " +
            "((cp.reservationInfo.reservationStartTime <= :startDateTime AND :startDateTime <= cp.reservationInfo.reservationEndTime) OR (cp.reservationInfo.reservationStartTime <= :endDateTime AND :endDateTime <= cp.reservationInfo.reservationEndTime)) AND cp.isDeleted = false " +
            "THEN 1" +
            "ELSE null " +
            "END), -1) "+
            "ORDER BY CAST(COALESCE(MIN(ro.defaultPrice), 0) as Long) ASC",
            countQuery = "SELECT COUNT(a) " +
                    "FROM Accommodation a " +
                    "LEFT OUTER JOIN RoomOption ro ON a.id = ro.accommodation.id " +
                    "LEFT OUTER JOIN CartProduct cp ON cp.roomOption.id = ro.id " +
                    "LEFT OUTER JOIN Cart c ON cp.cart.id = c.id " +
                    "LEFT OUTER JOIN Price p ON ro.id = p.roomOption.id " +
                    "WHERE " +
                    "ro.capacity >= :guest AND " +
                    " :startDate <= :endDate " +
                    "GROUP BY a.id HAVING COALESCE(SUM(ro.totalRoomCount), 0) > COALESCE(COUNT(case " +
                    "when (c.cartStatus = 'PENDING' OR c.cartStatus = 'PAID') AND  " +
                    "((cp.reservationInfo.reservationStartTime <= :startDateTime AND :startDateTime <= cp.reservationInfo.reservationEndTime) OR (cp.reservationInfo.reservationStartTime <= :endDateTime AND :endDateTime <= cp.reservationInfo.reservationEndTime)) AND cp.isDeleted = false " +
                    "THEN 1" +
                    "ELSE null " +
                    "END), -1)"

    )
    Page<AccommodationResponse> getAccommodations(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime,
            @Param("guest") Long guest,
            Pageable pageable);

    @Query(value = "SELECT NEW com.ybe.mp10.domain.accommodation.dto.response.AccommodationResponse(" +
            "a.id, " +
            "a.name, " +
            "a.thumbnailImageUrl, " +
            "a.category, " +
            "a.region, " +
            "CAST(COALESCE(MIN(ro.defaultPrice), 0) as Long) , " +
            "CAST(COALESCE(MAX(ro.defaultPrice), 500000) as Long), " +
            "CAST(SUM(DISTINCT CASE WHEN ((p.startDate <= :startDate AND :startDate <= p.endDate) OR (p.startDate <= :endDate AND :endDate <= p.endDate)) THEN p.price ELSE 0 END) AS LONG)" +
            ") FROM Accommodation a " +
            "LEFT OUTER JOIN RoomOption ro ON a.id = ro.accommodation.id " +
            "LEFT OUTER JOIN CartProduct cp ON cp.roomOption.id = ro.id " +
            "LEFT OUTER JOIN Cart c ON cp.cart.id = c.id " +
            "LEFT OUTER JOIN Price p ON ro.id = p.roomOption.id " +
            "WHERE " +
            "a.name LIKE concat('%',:name, '%') " +
            "AND ro.capacity >= :guest " +
            "GROUP BY a.id HAVING COALESCE(SUM(ro.totalRoomCount), 0) > COALESCE(COUNT(case " +
            "when c.cartStatus = 'PAID' AND  " +
            "((cp.reservationInfo.reservationStartTime <= :startDateTime AND :startDateTime <= cp.reservationInfo.reservationEndTime) OR (cp.reservationInfo.reservationStartTime <= :endDateTime AND :endDateTime <= cp.reservationInfo.reservationEndTime)) AND cp.isDeleted = false " +
            "THEN 1" +
            "ELSE null " +
            "END), -1) "+
            "ORDER BY CAST(COALESCE(MIN(ro.defaultPrice), 0) as Long) ASC",
            countQuery =
                    "SELECT COUNT(a) FROM Accommodation a " +
                            "LEFT OUTER JOIN RoomOption ro ON a.id = ro.accommodation.id " +
                            "LEFT OUTER JOIN CartProduct cp ON ro.id = cp.roomOption.id  " +
                            "LEFT OUTER JOIN Cart c ON cp.cart.id = c.id " +
                            "LEFT OUTER JOIN Price p ON ro.id = p.roomOption.id " +
                            "WHERE " +
                            "a.name = :name " +
                            "AND ro.capacity >= :guest " +
                            "AND :startDate <= :endDate " +
                            "GROUP BY a.id HAVING COALESCE(SUM(ro.totalRoomCount), 0) > COALESCE(COUNT(CASE " +
                            "WHEN c.cartStatus = 'PAID' AND  " +
                            "((cp.reservationInfo.reservationStartTime <= :startDateTime AND :startDateTime <= cp.reservationInfo.reservationEndTime) OR (cp.reservationInfo.reservationStartTime <= :endDateTime AND :endDateTime <= cp.reservationInfo.reservationEndTime)) AND cp.isDeleted = false " +
                            "THEN 1" +
                            "ELSE null " +
                            "END), -1)"
    )
    Page<AccommodationResponse> getAccommodationsByName(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime,
            @Param("guest") Long guest,
            @Param("name") String name,
            Pageable pageable);

    @Query(value = "SELECT NEW com.ybe.mp10.domain.accommodation.dto.response.AccommodationResponse(" +
            "a.id, " +
            "a.name, " +
            "a.thumbnailImageUrl, " +
            "a.category, " +
            "a.region, " +
            "CAST(COALESCE(MIN(ro.defaultPrice), 0) as Long) , " +
            "CAST(COALESCE(MAX(ro.defaultPrice), 500000) as Long), " +
            "CAST(SUM(DISTINCT CASE WHEN ((p.startDate <= :startDate AND :startDate <= p.endDate) OR (p.startDate <= :endDate AND :endDate <= p.endDate)) THEN p.price ELSE 0 END) AS LONG)" +
            ") FROM Accommodation a " +
            "LEFT OUTER JOIN RoomOption ro ON a.id = ro.accommodation.id " +
            "LEFT OUTER JOIN CartProduct cp ON cp.roomOption.id = ro.id " +
            "LEFT OUTER JOIN Cart c ON cp.cart.id = c.id " +
            "LEFT OUTER JOIN Price p ON ro.id = p.roomOption.id " +
            "WHERE " +
            "ro.capacity >= :guest " +
            "AND a.region = :region " +
            "GROUP BY a.id HAVING COALESCE(SUM(ro.totalRoomCount), 0) > COALESCE(COUNT(case " +
            "when c.cartStatus = 'PAID' AND  " +
            "((cp.reservationInfo.reservationStartTime <= :startDateTime AND :startDateTime <= cp.reservationInfo.reservationEndTime) OR (cp.reservationInfo.reservationStartTime <= :endDateTime AND :endDateTime <= cp.reservationInfo.reservationEndTime)) AND cp.isDeleted = false " +
            "THEN 1" +
            "ELSE null " +
            "END), -1) "+
            "ORDER BY CAST(COALESCE(MIN(ro.defaultPrice), 0) as Long) ASC",
            countQuery =
                    "SELECT COUNT(a) FROM Accommodation a " +
                            "LEFT OUTER JOIN RoomOption ro ON a.id = ro.accommodation.id " +
                            "LEFT OUTER JOIN CartProduct cp ON ro.id = cp.roomOption.id  " +
                            "LEFT OUTER JOIN Cart c ON cp.cart.id = c.id " +
                            "LEFT OUTER JOIN Price p ON ro.id = p.roomOption.id " +
                            "WHERE " +
                            "a.region = :region " +
                            "AND ro.capacity >= :guest " +
                            "AND :startDate <= :endDate " +
                            "GROUP BY a.id HAVING COALESCE(SUM(ro.totalRoomCount), 0) > COALESCE(COUNT(CASE " +
                            "WHEN c.cartStatus = 'PAID' AND  " +
                            "((cp.reservationInfo.reservationStartTime <= :startDateTime AND :startDateTime <= cp.reservationInfo.reservationEndTime) OR (cp.reservationInfo.reservationStartTime <= :endDateTime AND :endDateTime <= cp.reservationInfo.reservationEndTime)) AND cp.isDeleted = false " +
                            "THEN 1" +
                            "ELSE null " +
                            "END), -1)"
    )
    Page<AccommodationResponse> getAccommodationsByRegion(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate, @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime,
            @Param("guest") Long guest,
            @Param("region") Region region,
            Pageable pageable);

    @Query(value = "SELECT NEW com.ybe.mp10.domain.accommodation.dto.response.AccommodationResponse(" +
            "a.id, " +
            "a.name, " +
            "a.thumbnailImageUrl, " +
            "a.category, " +
            "a.region, " +
            "CAST(COALESCE(MIN(ro.defaultPrice), 0) as Long) , " +
            "CAST(COALESCE(MAX(ro.defaultPrice), 500000) as Long), " +
            "CAST(SUM(DISTINCT CASE WHEN ((p.startDate <= :startDate AND :startDate <= p.endDate) OR (p.startDate <= :endDate AND :endDate <= p.endDate)) THEN p.price ELSE 0 END) AS LONG)" +
            ") FROM Accommodation a " +
            "LEFT OUTER JOIN RoomOption ro ON a.id = ro.accommodation.id " +
            "LEFT OUTER JOIN CartProduct cp ON cp.roomOption.id = ro.id " +
            "LEFT OUTER JOIN Cart c ON cp.cart.id = c.id " +
            "LEFT OUTER JOIN Price p ON ro.id = p.roomOption.id " +
            "WHERE " +
            "a.category = :category " +
            "AND ro.capacity >= :guest " +
            "GROUP BY a.id HAVING COALESCE(SUM(ro.totalRoomCount), 0) > COALESCE(COUNT(case " +
            "when c.cartStatus = 'PAID' AND  " +
            "((cp.reservationInfo.reservationStartTime <= :startDateTime AND :startDateTime <= cp.reservationInfo.reservationEndTime) OR (cp.reservationInfo.reservationStartTime <= :endDateTime AND :endDateTime <= cp.reservationInfo.reservationEndTime)) AND cp.isDeleted = false " +
            "THEN 1" +
            "ELSE null " +
            "END), -1) "+
            "ORDER BY CAST(COALESCE(MIN(ro.defaultPrice), 0) as Long) ASC",
            countQuery =
                    "SELECT COUNT(a) FROM Accommodation a " +
                            "LEFT OUTER JOIN RoomOption ro ON a.id = ro.accommodation.id " +
                            "LEFT OUTER JOIN CartProduct cp ON ro.id = cp.roomOption.id  " +
                            "LEFT OUTER JOIN Cart c ON cp.cart.id = c.id " +
                            "LEFT OUTER JOIN Price p ON ro.id = p.roomOption.id " +
                            "WHERE " +
                            "a.category = :category " +
                            "AND ro.capacity >= :guest " +
                            "AND :startDate <= :endDate " +
                            "GROUP BY a.id HAVING COALESCE(SUM(ro.totalRoomCount), 0) > COALESCE(COUNT(CASE " +
                            "WHEN c.cartStatus = 'PAID' AND  " +
                            "((cp.reservationInfo.reservationStartTime <= :startDateTime AND :startDateTime <= cp.reservationInfo.reservationEndTime) OR (cp.reservationInfo.reservationStartTime <= :endDateTime AND :endDateTime <= cp.reservationInfo.reservationEndTime)) AND cp.isDeleted = false " +
                            "THEN 1" +
                            "ELSE null " +
                            "END), -1)"
    )
    Page<AccommodationResponse> getAccommodationsByCategory(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime,
            @Param("guest") Long guest,
            @Param("category") Category category,
            Pageable pageable);

    @Query(value = "SELECT NEW com.ybe.mp10.domain.accommodation.dto.response.AccommodationResponse(" +
            "a.id, " +
            "a.name, " +
            "a.thumbnailImageUrl, " +
            "a.category, " +
            "a.region, " +
            "CAST(COALESCE(MIN(ro.defaultPrice), 0) as Long) , " +
            "CAST(COALESCE(MAX(ro.defaultPrice), 500000) as Long)," +
            "CAST(SUM(DISTINCT CASE WHEN ((p.startDate <= :startDate AND :startDate <= p.endDate) OR (p.startDate <= :endDate AND :endDate <= p.endDate)) THEN p.price ELSE 0 END) AS LONG)" +
            ") FROM Accommodation a " +
            "LEFT OUTER JOIN RoomOption ro ON a.id = ro.accommodation.id " +
            "LEFT OUTER JOIN CartProduct cp ON cp.roomOption.id = ro.id " +
            "LEFT OUTER JOIN Cart c ON cp.cart.id = c.id " +
            "LEFT OUTER JOIN Price p ON ro.id = p.roomOption.id " +
            "WHERE " +
            "a.category = :category " +
            "AND a.region = :region " +
            "AND ro.capacity >= :guest " +
            "GROUP BY a.id HAVING COALESCE(SUM(ro.totalRoomCount), 0) > COALESCE(COUNT(CASE " +
            "WHEN c.cartStatus = 'PAID' AND  " +
            "((cp.reservationInfo.reservationStartTime <= :startDateTime AND :startDateTime <= cp.reservationInfo.reservationEndTime) OR (cp.reservationInfo.reservationStartTime <= :endDateTime AND :endDateTime <= cp.reservationInfo.reservationEndTime)) AND cp.isDeleted = false " +
            "THEN 1" +
            "ELSE null " +
            "END), -1) "+
            "ORDER BY CAST(COALESCE(MIN(ro.defaultPrice), 0) as Long) ASC",
            countQuery =
            "SELECT COUNT(a) FROM Accommodation a " +
                    "LEFT OUTER JOIN RoomOption ro ON a.id = ro.accommodation.id " +
                    "LEFT OUTER JOIN CartProduct cp ON ro.id = cp.roomOption.id  " +
                    "LEFT OUTER JOIN Cart c ON cp.cart.id = c.id " +
                    "LEFT OUTER JOIN Price p ON ro.id = p.roomOption.id " +
                    "WHERE " +
                    "a.category = :category " +
                    "AND a.region = :region " +
                    "AND ro.capacity >= :guest " +
                    "AND :startDate <= :endDate " +
                    "GROUP BY a.id HAVING COALESCE(SUM(ro.totalRoomCount), 0) > COALESCE(COUNT(CASE " +
                    "WHEN c.cartStatus = 'PAID' AND  " +
                    "((cp.reservationInfo.reservationStartTime <= :startDateTime AND :startDateTime <= cp.reservationInfo.reservationEndTime) OR (cp.reservationInfo.reservationStartTime <= :endDateTime AND :endDateTime <= cp.reservationInfo.reservationEndTime)) AND cp.isDeleted = false " +
                    "THEN 1" +
                    "ELSE null " +
                    "END), -1)"
    )
    Page<AccommodationResponse> getRelatedAccommodations(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime,
            @Param("guest") Long guest,
            @Param("region") Region region,
            @Param("category") Category category,
            Pageable pageable);

    @Query(value = "SELECT NEW com.ybe.mp10.domain.accommodation.dto.response.AccommodationResponse(" +
            "a.id, " +
            "a.name, " +
            "a.thumbnailImageUrl, " +
            "a.category, " +
            "a.region, " +
            "CAST(COALESCE(MIN(ro.defaultPrice), 0) as Long) , " +
            "CAST(COALESCE(MAX(ro.defaultPrice), 500000) as Long)," +
            "CAST(SUM(DISTINCT CASE WHEN ((p.startDate <= :startDate AND :startDate <= p.endDate) OR (p.startDate <= :endDate AND :endDate <= p.endDate)) THEN p.price ELSE 0 END) AS LONG)" +
            ") FROM Accommodation a " +
            "LEFT OUTER JOIN RoomOption ro ON a.id = ro.accommodation.id " +
            "LEFT OUTER JOIN CartProduct cp ON cp.roomOption.id = ro.id " +
            "LEFT OUTER JOIN Cart c ON cp.cart.id = c.id " +
            "LEFT OUTER JOIN Price p ON ro.id = p.roomOption.id " +
            "WHERE " +
            "a.region = :region " +
            "AND ro.capacity >= :guest " +
            "GROUP BY a.id HAVING COALESCE(SUM(ro.totalRoomCount), 0) > COALESCE(COUNT(case " +
            "when c.cartStatus = 'PAID' AND  " +
            "((cp.reservationInfo.reservationStartTime <= :startDateTime AND :startDateTime <= cp.reservationInfo.reservationEndTime) OR (cp.reservationInfo.reservationStartTime <= :endDateTime AND :endDateTime <= cp.reservationInfo.reservationEndTime)) AND cp.isDeleted = false " +
            "THEN 1" +
            "ELSE null " +
            "END), -1) "+
            "ORDER BY CAST(COALESCE(MIN(ro.defaultPrice), 0) as Long) ASC",
            countQuery =
                    "SELECT COUNT(a) FROM Accommodation a " +
                            "LEFT OUTER JOIN RoomOption ro ON a.id = ro.accommodation.id " +
                            "LEFT OUTER JOIN CartProduct cp ON ro.id = cp.roomOption.id  " +
                            "LEFT OUTER JOIN Cart c ON cp.cart.id = c.id " +
                            "LEFT OUTER JOIN Price p ON ro.id = p.roomOption.id " +
                            "WHERE " +
                            "a.region = :region " +
                            "AND ro.capacity >= :guest " +
                            "AND :startDate <= :endDate " +
                            "GROUP BY a.id HAVING COALESCE(SUM(ro.totalRoomCount), 0) > COALESCE(COUNT(CASE " +
                            "WHEN c.cartStatus = 'PAID' AND  " +
                            "((cp.reservationInfo.reservationStartTime <= :startDateTime AND :startDateTime <= cp.reservationInfo.reservationEndTime) OR (cp.reservationInfo.reservationStartTime <= :endDateTime AND :endDateTime <= cp.reservationInfo.reservationEndTime)) AND cp.isDeleted = false " +
                            "THEN 1" +
                            "ELSE null " +
                            "END), -1)"
    )
    Page<AccommodationResponse> getRecommendedRegionAccommodation(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime,
            @Param("guest") Long guest,
            @Param("region") Region region,
            Pageable pageable);

    @Query(value = "SELECT NEW com.ybe.mp10.domain.accommodation.dto.response.AccommodationResponse(" +
            "a.id, " +
            "a.name, " +
            "a.thumbnailImageUrl, " +
            "a.category, " +
            "a.region, " +
            "CAST(COALESCE(MIN(ro.defaultPrice), 0) as Long) , " +
            "CAST(COALESCE(MAX(ro.defaultPrice), 500000) as Long)," +
            "CAST(SUM(DISTINCT CASE WHEN ((p.startDate <= :startDate AND :startDate <= p.endDate) OR (p.startDate <= :endDate AND :endDate <= p.endDate)) THEN p.price ELSE 0 END) AS LONG)" +
            ") FROM Accommodation a " +
            "LEFT OUTER JOIN RoomOption ro ON a.id = ro.accommodation.id " +
            "LEFT OUTER JOIN CartProduct cp ON cp.roomOption.id = ro.id " +
            "LEFT OUTER JOIN Cart c ON cp.cart.id = c.id " +
            "LEFT OUTER JOIN Price p ON ro.id = p.roomOption.id " +
            "WHERE " +
            "ro.capacity >= :guest " +
            "GROUP BY a.id HAVING COALESCE(SUM(ro.totalRoomCount), 0) > COALESCE(COUNT(case " +
            "when c.cartStatus = 'PAID' AND  " +
            "((cp.reservationInfo.reservationStartTime <= :startDateTime AND :startDateTime <= cp.reservationInfo.reservationEndTime) OR (cp.reservationInfo.reservationStartTime <= :endDateTime AND :endDateTime <= cp.reservationInfo.reservationEndTime)) AND cp.isDeleted = false " +
            "THEN 1" +
            "ELSE null " +
            "END), -1) " +
            "ORDER BY CAST(COALESCE(COUNT(cp.id), 0) as Long) DESC ",
            countQuery =
                    "SELECT COUNT(a) FROM Accommodation a " +
                            "LEFT OUTER JOIN RoomOption ro ON a.id = ro.accommodation.id " +
                            "LEFT OUTER JOIN CartProduct cp ON ro.id = cp.roomOption.id  " +
                            "LEFT OUTER JOIN Cart c ON cp.cart.id = c.id " +
                            "LEFT OUTER JOIN Price p ON ro.id = p.roomOption.id " +
                            "WHERE " +
                            "ro.capacity >= :guest " +
                            "AND :startDate <= :endDate " +
                            "GROUP BY a.id HAVING COALESCE(SUM(ro.totalRoomCount), 0) > COALESCE(COUNT(CASE " +
                            "WHEN c.cartStatus = 'PAID' AND  " +
                            "((cp.reservationInfo.reservationStartTime <= :startDateTime AND :startDateTime <= cp.reservationInfo.reservationEndTime) OR (cp.reservationInfo.reservationStartTime <= :endDateTime AND :endDateTime <= cp.reservationInfo.reservationEndTime)) AND cp.isDeleted = false " +
                            "THEN 1" +
                            "ELSE null " +
                            "END), -1)"
    )
    Page<AccommodationResponse> getRankingAccommodations(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime,
            @Param("guest") Long guest,
            Pageable pageable);

}
