package com.ybe.mp10.domain.accommodation.dto.response;

import com.ybe.mp10.domain.accommodation.model.Accommodation;
import com.ybe.mp10.domain.accommodation.model.AccommodationImage;
import com.ybe.mp10.domain.roomoption.dto.response.RoomOptionResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "숙박 상품 상세 조회시 리턴 데이터")
public class AccommodationRoomOptionResponse {
    private Long id;
    private String name;
    private String category;
    private String region;
    private String address;
    private String description;
    private String thumbnailImageUrl;
    private List<RoomOptionResponse> roomOptions;

    public static AccommodationRoomOptionResponse fromEntity(Accommodation accommodation, List<RoomOptionResponse> roomOptions) {
        return AccommodationRoomOptionResponse.builder()
                .id(accommodation.getId())
                .name(accommodation.getName())
                .address(accommodation.getAddress())
                .category(accommodation.getCategory().getValue())
                .region(accommodation.getRegion().getValue())
                .description(accommodation.getDescription())
                .thumbnailImageUrl(accommodation.getThumbnailImageUrl())
                .roomOptions(roomOptions)
                .build();
    }

}
