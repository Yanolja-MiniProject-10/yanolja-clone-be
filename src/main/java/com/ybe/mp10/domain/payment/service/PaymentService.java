package com.ybe.mp10.domain.payment.service;

import com.ybe.mp10.domain.accommodation.model.Accommodation;
import com.ybe.mp10.domain.cart.dto.request.CartPaymentRequest;
import com.ybe.mp10.domain.cart.model.Cart;
import com.ybe.mp10.domain.cart.model.CartProduct;
import com.ybe.mp10.domain.cart.repository.CartProductRepository;
import com.ybe.mp10.domain.cart.repository.CartRepository;
import com.ybe.mp10.domain.payment.dto.request.*;
import com.ybe.mp10.domain.payment.dto.response.*;
import com.ybe.mp10.domain.payment.exception.*;
import com.ybe.mp10.domain.payment.model.Payment;
import com.ybe.mp10.domain.payment.model.PaymentProduct;
import com.ybe.mp10.domain.payment.repository.PaymentRepository;
import com.ybe.mp10.domain.roomoption.model.RoomOption;
import com.ybe.mp10.domain.roomoption.repository.RoomOptionRepository;
import com.ybe.mp10.domain.user.exception.UserException;
import com.ybe.mp10.domain.user.model.User;
import com.ybe.mp10.domain.user.repository.UserRepository;
import com.ybe.mp10.global.common.ReservationInfo;
import com.ybe.mp10.global.common.enums.CartStatus;
import com.ybe.mp10.global.common.enums.Transportation;
import com.ybe.mp10.global.security.token.UserPayload;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;
    private final RoomOptionRepository roomOptionRepository;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    public PaymentResponse cartPayment(CartPaymentRequest cartPaymentRequest) {

        Cart cart = cartRepository.findById(cartPaymentRequest.getCartId())
            .orElseThrow(NoSuchCartException::create);

        List<CartProduct> cartProducts = cart.getCartProducts();

        for (CartProduct cartProduct : cartProducts) {
            if (!cartPaymentRequest.getCartProducts().contains(cartProduct.getId())) {
                cartProduct.delete();
            }
        }

        List<PaymentAccommodationResponse> paymentAccommodationResponses = cartProductRepository.findAllAccommodationByCartId(
                cart.getId()).stream().map(PaymentAccommodationResponse::toAccommodationEntity)
            .toList();

        Long count = 0L;

        for (CartProduct cartProduct : cartProducts) {

            if (cartProduct.getIsDeleted()) {
                continue;
            }

            PaymentRoomOptionResponse paymentRoomOptionResponse = PaymentRoomOptionResponse.fromEntity(
                cartProduct);

            Long accommodationId = cartProduct.getRoomOption().getAccommodation().getId();

            for (PaymentAccommodationResponse paymentAccommodationResponse : paymentAccommodationResponses) {
                if (paymentAccommodationResponse.getAccommodationId().equals(accommodationId)) {
                    paymentAccommodationResponse.getRoomOptions().add(paymentRoomOptionResponse);
                    count++;
                }
            }
        }

        cart.setCartStatus(CartStatus.NONE);

        return PaymentResponse.builder()
            .cartId(cart.getId())
            .count(count)
            .accommodations(paymentAccommodationResponses).build();
    }

    public PaymentResponse instantPayment(InstantPaymentRequest instantPaymentRequest,
        UserPayload userPayload) {

        User user = userRepository.findById(userPayload.id())
            .orElseThrow(() -> new UserException("사용자가 없습니다.", HttpStatus.UNAUTHORIZED));

        Cart cart = cartRepository.save(
            Cart.builder()
                .user(user)
                .cartStatus(CartStatus.INSTANT)
                .build());

        RoomOption roomOption = roomOptionRepository.findById(
                instantPaymentRequest.getRoomOptionId())
            .orElseThrow(NoSuchRoomOptionException::create);

        CartProduct cartProduct = cartProductRepository.save(
            CartProduct.builder()
                .cart(cart)
                .roomOption(roomOption)
                .reservationInfo(
                    ReservationInfo.builder()
                        .stayDuration(instantPaymentRequest.getStayDuration())
                        .reservationStartTime(
                            instantPaymentRequest.getReservationStartDate().atStartOfDay())
                        .reservationEndTime(
                            instantPaymentRequest.getReservationEndDate().atStartOfDay())
                        .numberOfGuest(instantPaymentRequest.getNumberOfGuest())
                        .build())
                .build());

        Accommodation accommodation = cartProduct.getRoomOption().getAccommodation();

        PaymentRoomOptionResponse paymentRoomOptionResponse = PaymentRoomOptionResponse.fromEntity(
            cartProduct);

        PaymentAccommodationResponse paymentAccommodationResponse = PaymentAccommodationResponse.toAccommodationEntity(
            accommodation);

        paymentAccommodationResponse.getRoomOptions().add(paymentRoomOptionResponse);

        Long count = (long) paymentAccommodationResponse.getRoomOptions().size();

        return PaymentResponse.builder()
            .cartId(cart.getId())
            .count(count)
            .accommodations(List.of(paymentAccommodationResponse))
            .build();
    }

    public void payment(PaymentRequest paymentRequest, UserPayload userPayload) {

        User user = userRepository.findById(userPayload.id())
            .orElseThrow(() -> new UserException("사용자가 없습니다.", HttpStatus.UNAUTHORIZED));

        Cart cart = cartRepository.findById(paymentRequest.getCartId())
            .orElseThrow(NoSuchCartException::create);

        List<CartProduct> cartProducts = new ArrayList<>();
        for (PaymentCartProduct paymentCartProduct : paymentRequest.getCartProducts()) {

            CartProduct cartProduct = cartProductRepository.findById(
                    paymentCartProduct.getCartProductId())
                .orElseThrow(NoSuchCartProductException::create);

            cartProduct.setTransportation(
                Transportation.from(paymentCartProduct.getTransportation()));

            cartProducts.add(cartProduct);
        }

        Long paymentAmount = cartProducts.stream().mapToLong(
                cartProduct -> cartProduct.getReservationInfo().getStayDuration()
                    * cartProduct.getRoomOption().getDefaultPrice()).reduce(Long::sum)
            .orElseThrow(IllegalArgumentException::new);

        String reservationNumber = generateReservationNumber(cart.getId());

        List<PaymentProduct> paymentProducts = cartProducts.stream().map(PaymentProduct::toEntity)
            .toList();

        Payment payment = Payment.builder()
            .paymentAmount(paymentAmount)
            .paymentCanceled(false)
            .reservationNumber(reservationNumber)
            .paymentProducts(paymentProducts)
            .cartId(cart.getId())
            .user(user).build();

        for (PaymentProduct paymentProduct : paymentProducts) {
            paymentProduct.setPayment(payment);
        }

        paymentRepository.save(payment);
        cart.setCartStatus(CartStatus.PAID);
//        paymentProductRepository.saveAll(paymentProducts);

    }

    private String generateReservationNumber(Long id) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + id;
    }

    public List<PaymentResultResponse> selectPayments(UserPayload userPayload) {

        List<Payment> payments = paymentRepository.findAllByUserIdOrderByCreatedTimeDesc(
            userPayload.id());

        List<PaymentResultResponse> paymentResultResponses = new ArrayList<>();

        for (Payment payment : payments) {

            List<PaymentProduct> paymentProducts = payment.getPaymentProducts();

            List<PaymentResultAccommodationResponse> accommodations =
                paymentRepository.getPaymentAccommodation(userPayload.id(), payment.getId())
                    .stream()
                    .map(PaymentResultAccommodationResponse::fromEntity)
                    .toList();

            PaymentResultResponse paymentResultResponse = PaymentResultResponse
                .fromEntity(payment, accommodations);

            for (PaymentProduct paymentProduct : paymentProducts) {

                PaymentResultRoomOptionResponse paymentProductAsRoomOption =
                    paymentRepository.getPaymentProductAsRoomOption(paymentProduct.getId());

                paymentResultResponse
                    .findAccommodation(paymentProduct.getAccommodationId())
                    .getRoomOptions()
                    .add(paymentProductAsRoomOption);
            }
            paymentResultResponses.add(paymentResultResponse);

        }

        return paymentResultResponses;
    }


    public void deletePayment(DeletePaymentRequest deletePaymentRequest) {

        Payment payment = paymentRepository.findById(deletePaymentRequest.getPaymentId())
            .orElseThrow(NoSuchPaymentException::create);

        payment.setPaymentCanceled(true);

    }

    public PaymentVerificationResponse verifyPayment(PaymentVerificationRequest paymentVerificationRequest) {
        Cart cart = cartRepository.findById(paymentVerificationRequest.getCartId()).orElseThrow(() -> new PaymentException("유효한 카트 아이디가 아닙니다.", NOT_FOUND));
        List<CartProduct> cartProducts = cartProductRepository.findAllByCartId(cart.getId());
        Long priceSum = 0L;
        for (CartProduct cartProduct : cartProducts) {
            Long defaultPrice = cartProduct.getRoomOption().getDefaultPrice();
            Long stayDuration = cartProduct.getReservationInfo().getStayDuration();
            priceSum += defaultPrice * stayDuration;
        }
        if (priceSum.equals(paymentVerificationRequest.getTotalPrice())) {
            return new PaymentVerificationResponse(true);
        } else {
            return new PaymentVerificationResponse(false);
        }

    }
}
