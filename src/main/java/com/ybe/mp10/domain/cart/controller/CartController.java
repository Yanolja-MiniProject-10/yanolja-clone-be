package com.ybe.mp10.domain.cart.controller;

import static com.ybe.mp10.global.common.constant.ResponseConstant.SUCCESS;

import com.ybe.mp10.domain.cart.dto.request.CartDeleteRequest;
import com.ybe.mp10.domain.cart.dto.request.CartProductRequest;
import com.ybe.mp10.domain.cart.dto.response.CartAccommodationResponse;
import com.ybe.mp10.domain.cart.dto.response.CartResponse;
import com.ybe.mp10.domain.cart.service.CartService;
import com.ybe.mp10.global.response.GlobalDataResponse;
import com.ybe.mp10.global.response.GlobalResponse;
import com.ybe.mp10.global.security.token.UserPayload;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
@Tag(name = "장바구니", description = "장바구니 관련 API")
public class CartController {

    private final CartService cartService;

    @GetMapping
    @Operation(summary = "장바구니 상품 조회")
    @ApiResponse(responseCode = "200", description = "조회 성공",
        content = {@Content(
            array = @ArraySchema(schema = @Schema(implementation = CartAccommodationResponse.class))
        )})
    public GlobalDataResponse<CartResponse> selectCarts(
        @AuthenticationPrincipal UserPayload userPayload
    ) {
        return GlobalDataResponse.ok(SUCCESS, cartService.selectCarts(userPayload));
    }

    @PostMapping
    @Operation(summary = "장바구니 상품 저장")
    @ApiResponse(responseCode = "200", description = "저장 성공",
        content = {@Content(schema = @Schema(implementation = String.class))})
    public GlobalResponse insertCarts(
        @RequestBody @Valid CartProductRequest cartProductRequest,
        @AuthenticationPrincipal UserPayload userPayload
    ) {

        cartService.insertCarts(cartProductRequest, userPayload);

        return GlobalResponse.ok(SUCCESS);
    }

    @DeleteMapping
    @Operation(summary = "장바구니 상품 삭제")
    @ApiResponse(responseCode = "200", description = "삭제 성공",
        content = {@Content(schema = @Schema(implementation = String.class))})
    public GlobalResponse deleteCarts(
        @RequestBody final CartDeleteRequest cartDeleteRequests,
        @AuthenticationPrincipal UserPayload userPayload
    ) {

        cartService.deleteCarts(cartDeleteRequests);

        return GlobalResponse.ok(SUCCESS);
    }


}
