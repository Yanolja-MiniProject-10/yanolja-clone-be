package com.ybe.mp10.domain.roomoption.dto.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ybe.mp10.domain.roomoption.exception.RoomOptionException;
import com.ybe.mp10.domain.roomoption.model.RoomOption;
import com.ybe.mp10.domain.roomoption.model.RoomOptionImage;
import com.ybe.mp10.domain.roomoption.repository.RoomOptionRepository;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Convert;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static org.springframework.http.HttpStatus.CONFLICT;

@Getter
@NoArgsConstructor
@Schema(description = "숙박 상품 상세 조회시 룸 옵션 리턴 데이터")
@Slf4j
public class RoomOptionResponse {
    private Long id;
    private Long accommodationId;
    private String name;
    @Convert(converter = RoomOptionImage.RoomOptionImageConverter.class)
    private RoomOptionImage roomOptionImage;
    private Long capacity;
    private Long totalRoomCount;
    private Long reservedRoomCount;
    private String description;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;
    private Long totalPrice;
    private Long stayDuration;
    @Builder
    public RoomOptionResponse(Long id, Long accommodationId, String name, RoomOptionImage roomOptionImage, Long capacity, Long totalRoomCount, Long reservedRoomCount, String description, LocalTime checkInTime, LocalTime checkOutTime, Long dailyPrice, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.accommodationId = accommodationId;
        this.name = name;
        this.roomOptionImage = roomOptionImage;
        this.capacity = capacity;
        this.totalRoomCount = totalRoomCount;
        this.reservedRoomCount = reservedRoomCount;
        this.description = description;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.stayDuration = ChronoUnit.DAYS.between(startDate, endDate);
        this.totalPrice = dailyPrice * stayDuration;
    }

    public RoomOptionResponse(RoomOptionRepository.GetRoomOptionResponse getRoomOptionResponse, ObjectMapper mapper)  {
        this.id = getRoomOptionResponse.getRoomOptionId();
        this.accommodationId = getRoomOptionResponse.getAccommodationId();
        this.name = getRoomOptionResponse.getName();
        try {
            this.roomOptionImage = mapper.readValue(getRoomOptionResponse.getRoomOptionImage(), RoomOptionImage.class);
        } catch (JsonProcessingException e) {
            throw new RoomOptionException(this.id + " 룸 옵션 이미지를 파싱하는 과정에서 에러가 발생했습니다.", CONFLICT);
        }
        this.capacity = getRoomOptionResponse.getCapacity();
        this.totalRoomCount = getRoomOptionResponse.getTotalRoomCount();
        this.reservedRoomCount = getRoomOptionResponse.getReservedRoomCount();
        this.description = getRoomOptionResponse.getDescription();
        this.checkInTime = getRoomOptionResponse.getCheckInTime();
        this.checkOutTime = getRoomOptionResponse.getCheckOutTime();
        this.totalPrice  = getRoomOptionResponse.getTotalPrice();
        this.stayDuration = getRoomOptionResponse.getStayDuration();
    }

    public void addToTotalPrice(Long prices) {
        this.totalPrice += prices;
    }
}
