package com.ybe.mp10.domain.festival.repository;

import com.ybe.mp10.domain.festival.dto.response.FestivalThumbnailResponse;
import com.ybe.mp10.domain.festival.model.Festival;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface FestivalRepository extends JpaRepository<Festival, Long> {
    @Query("SELECT new com.ybe.mp10.domain.festival.dto.response.FestivalThumbnailResponse(" +
            "f.id, " +
            "f.title, " +
            "f.thumbnailImageUrl, " +
            "f.eventStartDate, " +
            "f.eventEndDate, " +
            "f.address, " +
            "f.city, " +
            "f.area, " +
            "f.phoneNumber) " +
            "FROM Festival f WHERE f.eventEndDate >= :eventStartDate ORDER BY f.eventStartDate ASC")
    Page<FestivalThumbnailResponse> findFestivalResponseByEventStartDate(@Param("eventStartDate") LocalDate eventStartDate, Pageable pageable);

    void deleteFestivalBySigunguCode(String sigunguCode);
}
