package com.ybe.mp10.domain.accommodation.dto.request;

import com.ybe.mp10.domain.accommodation.model.Accommodation;
import com.ybe.mp10.domain.roomoption.dto.request.RoomOptionCreateRequest;
import com.ybe.mp10.domain.roomoption.model.RoomOption;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoomOptionRequest {
    private Long defaultPrice;
    private String description;
    private LocalTime checkIn;
    private LocalTime checkOut;
    private Long totalRoomCount;
    private String name;
    private Long capacity;
    private Long accommodationId;

    public static RoomOption toEntity(RoomOptionRequest roomOptionRequest) {
        return RoomOption.builder()
                .defaultPrice(roomOptionRequest.getDefaultPrice())
                .description(roomOptionRequest.getDescription())
                .checkInTime(roomOptionRequest.getCheckIn())
                .checkOutTime(roomOptionRequest.getCheckOut())
                .totalRoomCount(roomOptionRequest.getTotalRoomCount())
                .name(roomOptionRequest.getName())
                .capacity(roomOptionRequest.getCapacity())
                .accommodation(
                        Accommodation.builder().id(roomOptionRequest.getAccommodationId()).build())
                .build();
    }
}
