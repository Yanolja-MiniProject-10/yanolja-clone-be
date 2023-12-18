package com.ybe.mp10.domain.roomoption.dto.request;

import com.ybe.mp10.domain.accommodation.model.Accommodation;
import com.ybe.mp10.domain.roomoption.model.RoomOption;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoomOptionCreateRequest {

    private Long defaultPrice;
    private String roomOptionImage;
    private String description;
    private LocalTime checkin;
    private LocalTime checkOut;
    private Long totalRoomCount;
    private String name;
    private Long capacity;
    private Long accommodationId;
    private String thumnailImageUrl;

    public static RoomOption toEntity(RoomOptionCreateRequest roomOptionCreateRequest) {
        return RoomOption.builder()
            .defaultPrice(roomOptionCreateRequest.getDefaultPrice())
            .description(roomOptionCreateRequest.getDescription())
            .checkInTime(roomOptionCreateRequest.getCheckin())
            .checkOutTime(roomOptionCreateRequest.getCheckOut())
            .totalRoomCount(roomOptionCreateRequest.getTotalRoomCount())
            .name(roomOptionCreateRequest.getName())
            .capacity(roomOptionCreateRequest.getCapacity())
            .thumbnailImage(roomOptionCreateRequest.getThumnailImageUrl())
            .accommodation(
                Accommodation.builder().id(roomOptionCreateRequest.getAccommodationId()).build())
            .build();
    }

}
