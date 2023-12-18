package com.ybe.mp10.domain.roomoption.controller;

import static com.ybe.mp10.global.common.constant.ResponseConstant.SUCCESS;

import com.ybe.mp10.domain.roomoption.dto.request.PriceRequest;
import com.ybe.mp10.domain.roomoption.dto.request.RoomOptionCreateRequest;
import com.ybe.mp10.domain.roomoption.model.Price;
import com.ybe.mp10.domain.roomoption.model.RoomOption;
import com.ybe.mp10.domain.roomoption.service.RoomOptionService;
import com.ybe.mp10.global.response.GlobalDataResponse;
import com.ybe.mp10.global.response.GlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/roomoptions")
@RequiredArgsConstructor
@Tag(name = "방 옵션 관련 API", description = "숙박상품의 상세 방 옵션 관련 API입니다.")
public class RoomOptionController {

    private final RoomOptionService roomOptionService;

    @PostMapping
    @Operation(summary = "숙박상품 방상세옵션 생성 API(개발용)", description = "수동으로 숙박상품 옵션을 넣을때 사용합니다.")
    public GlobalDataResponse<RoomOption> createRoomOptions(
        @RequestPart("roomOption") RoomOptionCreateRequest roomOptionCreateRequest,
        @RequestPart("thumbnail") MultipartFile thumbNail,
        @RequestPart("roomOptionImages") List<MultipartFile> roomOptionImages) {

        return GlobalDataResponse.ok(SUCCESS,
            roomOptionService.createRoomOption(roomOptionCreateRequest, thumbNail,
                roomOptionImages));
    }

    @PostMapping("/price")
    @Operation(summary = "방상세옵션 기간별 가격 생성 API(개발용)", description = "수동으로 숙박상품의 가격을 설정 할때 사용합니다.")
    public GlobalDataResponse<Price> modifiedRoomOptionPrice(
        @RequestBody PriceRequest priceRequest
    ) {
        return GlobalDataResponse.ok(SUCCESS,
            roomOptionService.modifiedRoomOptionPrice(priceRequest));
    }

}
