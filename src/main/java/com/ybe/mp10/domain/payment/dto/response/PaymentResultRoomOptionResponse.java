package com.ybe.mp10.domain.payment.dto.response;

import com.ybe.mp10.global.common.enums.Transportation;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class PaymentResultRoomOptionResponse {

    @Schema(example = "1")
    private Long paymentProductId;

    @Schema(example = "2")
    private Long accommodationId;

    @Schema(example = "3")
    private Long roomOptionId;

    @Schema(example = "2인실(오션뷰)")
    private String name;
    @Schema(example = "https://s3.us-west-1.amazonaws.com/myBucket/object%20key?query=%5Bbrackets%5D")
    private String thumbnailImage;

//    @Schema(example = "10")
//    private Long totalRoomCount;

    //private Long reservedRoomCount;
    //TODO : 룸카운트와 예약된 룸카운트를 같이 보낸다. 같으면 예매 완료

    @Schema(example = "2")
    private Long capacity;

    @Schema(example = "20000")
    private Long pricePerNight;

    //TODO : 가격을 유동적으로 보내야 한다. TotalPrice를 보냄
    private Long totalPrice;

    @Schema(example = "2020-12-12")
    private LocalDate reservationStartDate;

    @Schema(example = "2020-12-15")
    private LocalDate reservationEndDate;

    @Schema(example = "3")
    private Long stayDuration;

    @Schema(example = "2")
    private Long numberOfGuest;

    @Schema(example = "도보")
    private Transportation transportation;

    public PaymentResultRoomOptionResponse(Long paymentProductId, Long accommodationId,
        Long roomOptionId, String name, String thumbnailImage, Long capacity, Long pricePerNight,
        Long totalPrice, LocalDateTime reservationStartDate, LocalDateTime reservationEndDate,
        Long stayDuration, Long numberOfGuest, Transportation transportation) {

        this(
            paymentProductId
            ,accommodationId
            ,roomOptionId
            ,name
            ,thumbnailImage
            ,capacity
            ,pricePerNight
            ,totalPrice
            ,reservationStartDate.toLocalDate()
            ,reservationEndDate.toLocalDate()
            ,stayDuration
            ,numberOfGuest
            ,transportation
        );
    }
    public PaymentResultRoomOptionResponse(Long paymentProductId, Long accommodationId,
        Long roomOptionId, String name, String thumbnailImage, Long capacity, Long pricePerNight,
        Long totalPrice, LocalDate reservationStartDate, LocalDate reservationEndDate,
        Long stayDuration, Long numberOfGuest, Transportation transportation) {
        this.paymentProductId = paymentProductId;
        this.accommodationId = accommodationId;
        this.roomOptionId = roomOptionId;
        this.name = name;
        this.thumbnailImage = thumbnailImage;
        this.capacity = capacity;
        this.pricePerNight = pricePerNight;
        this.totalPrice = totalPrice;
        this.reservationStartDate = reservationStartDate;
        this.reservationEndDate = reservationEndDate;
        this.stayDuration = stayDuration;
        this.numberOfGuest = numberOfGuest;
        this.transportation = transportation;
    }

}
