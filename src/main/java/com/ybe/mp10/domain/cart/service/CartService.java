package com.ybe.mp10.domain.cart.service;

import com.ybe.mp10.domain.accommodation.model.Accommodation;
import com.ybe.mp10.domain.cart.dto.request.CartDeleteRequest;
import com.ybe.mp10.domain.cart.dto.request.CartProductRequest;
import com.ybe.mp10.domain.cart.dto.response.CartAccommodationResponse;
import com.ybe.mp10.domain.cart.dto.response.CartResponse;
import com.ybe.mp10.domain.cart.dto.response.CartRoomOptionResponse;
import com.ybe.mp10.domain.cart.model.Cart;
import com.ybe.mp10.domain.cart.model.CartProduct;
import com.ybe.mp10.domain.cart.repository.CartProductRepository;
import com.ybe.mp10.domain.cart.repository.CartRepository;
import com.ybe.mp10.domain.user.exception.UserException;
import com.ybe.mp10.domain.user.model.User;
import com.ybe.mp10.domain.user.repository.UserRepository;
import com.ybe.mp10.global.common.enums.CartStatus;
import com.ybe.mp10.global.security.token.UserPayload;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;
    private final UserRepository userRepository;

    public CartResponse selectCarts(UserPayload userPayload) {

        //장바구니의 숙박상품별 옵션 그룹조회를 위해 숙소와 옵션을 따로 조회
        //숙소. 옵션을 각각 dto로 변환한뒤 숙소번호와 옵션의 숙소번호가 같은곳에 넣어준다

        User user = userRepository.findById(userPayload.id())
            .orElseThrow(() -> new UserException("사용자가 없습니다.", HttpStatus.UNAUTHORIZED));

        Cart cart = findCart(user);

        List<Accommodation> accommodations = cartProductRepository
            .findAllAccommodationByCartId(cart.getId());
        List<CartProduct> cartProducts = cartProductRepository.findAllByCartId(cart.getId());

        List<CartAccommodationResponse> cartAccommodationResponses = accommodations
            .stream()
            .map(CartAccommodationResponse::toAccommodationEntity)
            .toList();

        long count = 0L;

        for (CartProduct cartProduct : cartProducts) {

            CartRoomOptionResponse cartRoomOptionResponse =
                CartRoomOptionResponse.fromEntity(cartProduct);

            Long accommodationId = cartProduct.getRoomOption().getAccommodation().getId();

            for (CartAccommodationResponse cartAccommodationResponse : cartAccommodationResponses) {
                if (cartAccommodationResponse.getAccommodationId().equals(accommodationId)) {
                    cartAccommodationResponse.getRoomOptions().add(cartRoomOptionResponse);
                    count++;
                }
            }
        }

        return CartResponse.builder()
            .cartId(cart.getId())
            .count(count)
            .accommodations(cartAccommodationResponses)
            .build();
    }

    public void insertCarts(CartProductRequest cartProductRequestDtos, UserPayload userPayload) {

        User user = userRepository.findById(userPayload.id())
            .orElseThrow(() -> new UserException("사용자가 없습니다.", HttpStatus.UNAUTHORIZED));

        Cart cart = findCart(user);

        CartProduct cartProduct = CartProductRequest.toEntity(cartProductRequestDtos, cart);

        cartProductRepository.save(cartProduct);

    }


    public void deleteCarts(CartDeleteRequest cartDeleteRequests) {

        List<CartProduct> cartProducts = cartProductRepository
            .findAllById(cartDeleteRequests.getCartProducts());

        cartProducts.forEach(CartProduct::delete);

    }


    private Cart findCart(User user) {
        return Optional.ofNullable(cartRepository.findByUserId(user.getId()))
            .orElseGet(() -> cartRepository.save(Cart.builder()
                .cartStatus(CartStatus.NONE)
                .user(user)
                .build()));
    }

}
