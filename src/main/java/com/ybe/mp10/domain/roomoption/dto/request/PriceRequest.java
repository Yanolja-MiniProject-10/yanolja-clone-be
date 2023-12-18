package com.ybe.mp10.domain.roomoption.dto.request;

import com.ybe.mp10.domain.roomoption.model.Price;
import com.ybe.mp10.domain.roomoption.model.RoomOption;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceRequest {

    private Long roomOptionId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long price;
    private String description;

    public static Price toEntity(PriceRequest priceRequest) {
        return Price.builder()
            .roomOption(RoomOption.builder().id(priceRequest.roomOptionId).build())
            .startDate(priceRequest.getStartDate())
            .endDate(priceRequest.getEndDate())
            .description(priceRequest.getDescription())
            .price(priceRequest.getPrice())
            .build();
    }
}
