package com.ybe.mp10.domain.payment.controller;

import static com.ybe.mp10.global.common.constant.ResponseConstant.SUCCESS;

import com.ybe.mp10.domain.cart.dto.request.CartPaymentRequest;
import com.ybe.mp10.domain.payment.dto.request.PaymentVerificationRequest;
import com.ybe.mp10.domain.payment.dto.response.PaymentResponse;
import com.ybe.mp10.domain.payment.dto.request.DeletePaymentRequest;
import com.ybe.mp10.domain.payment.dto.request.InstantPaymentRequest;
import com.ybe.mp10.domain.payment.dto.request.PaymentRequest;
import com.ybe.mp10.domain.payment.dto.response.PaymentResultResponse;
import com.ybe.mp10.domain.payment.dto.response.PaymentVerificationResponse;
import com.ybe.mp10.domain.payment.service.PaymentService;
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
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@Tag(name = "결제 API", description = "결제 관련 API 입니다.")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/cart")
    @Operation(summary = "장바구니 결제 시작(장바구니 내용 결제)")
    @ApiResponse(responseCode = "200", description = "장바구니의 상품 결제 대기 상태로 변경 성공",
        content = {@Content(schema = @Schema(implementation = PaymentResponse.class))}
    )
    public GlobalDataResponse<PaymentResponse> cartPayment(
        @RequestBody @Valid CartPaymentRequest cartPaymentRequest
    ) {
        return GlobalDataResponse.ok(SUCCESS, paymentService.cartPayment(cartPaymentRequest));
    }

    @PostMapping("/instant")
    @Operation(summary = "즉시 결제 시작(목록에서 직접 결제)")
    @ApiResponse(responseCode = "200", description = "단일건 결제 대기 상태로 변경 성공",
        content = {@Content(schema = @Schema(implementation = PaymentResponse.class))}
    )
    public GlobalDataResponse<PaymentResponse> instantPayment(
        @RequestBody @Valid InstantPaymentRequest instantPaymentRequest,
        @AuthenticationPrincipal UserPayload userPayload
    ) {
        return GlobalDataResponse.ok(SUCCESS, paymentService
            .instantPayment(instantPaymentRequest, userPayload));
    }


    @PostMapping("/purchase")
    @Operation(summary = "결제 완료(주문,결제 창에서 결제 완료)")
    @ApiResponse(responseCode = "200", description = "최종 결제 성공",
        content = {@Content(schema = @Schema(implementation = String.class))}
    )
    public GlobalResponse payment(
        @RequestBody @Valid PaymentRequest paymentRequest,
        @AuthenticationPrincipal UserPayload userPayload
    ) {
        paymentService.payment(paymentRequest, userPayload);
        return GlobalResponse.ok(SUCCESS);
    }

    @PostMapping("/verification")
    @Operation(summary = "결제 검증")
    @ApiResponse(responseCode = "200", description = "결제 검증 성공",
            content = @Content(schema = @Schema(implementation = PaymentVerificationResponse.class)))
    public GlobalDataResponse<PaymentVerificationResponse> verifyPayment(@RequestBody PaymentVerificationRequest paymentVerificationRequest) {
        return GlobalDataResponse.ok(SUCCESS, paymentService.verifyPayment(paymentVerificationRequest));
    }


    @GetMapping
    @Operation(summary = "결제 내역 조회")
    @ApiResponse(responseCode = "200", description = "결제 내역 조회 성공",
        content = {@Content(
            array = @ArraySchema(schema = @Schema(implementation = PaymentResultResponse.class)))}
    )
    public GlobalDataResponse<List<PaymentResultResponse>> selectPayments(
        @AuthenticationPrincipal UserPayload userPayload
    ) {
        return GlobalDataResponse.ok(SUCCESS, paymentService.selectPayments(userPayload));
    }

    @DeleteMapping
    @Operation(summary = "결제 취소")
    @ApiResponse(responseCode = "200", description = "결제 취소 성공",
        content = {@Content(schema = @Schema(implementation = String.class))}
    )
    public GlobalResponse deletePayment(
        @RequestBody @Valid DeletePaymentRequest deletePaymentRequest
    ) {
        paymentService.deletePayment(deletePaymentRequest);
        return GlobalResponse.ok(SUCCESS);
    }


}
