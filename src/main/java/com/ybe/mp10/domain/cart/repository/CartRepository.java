package com.ybe.mp10.domain.cart.repository;

import com.ybe.mp10.domain.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query(
        value = "select * from ( "
            + " select row_number() over (order by cart_id desc ) as num"
            + ", c.* from cart c"
            + " where c.user_id = :userId "
            + " and c.cart_status = 'NONE' ) a"
        + " where num = 1"
    , nativeQuery = true)
    Cart findByUserId(Long userId);

}
