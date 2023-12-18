package com.ybe.mp10.domain.cart.repository;

import com.ybe.mp10.domain.accommodation.model.Accommodation;
import com.ybe.mp10.domain.cart.model.CartProduct;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {

    @Query(
        "select c.roomOption.accommodation "
            + "from CartProduct c "
            + "where c.cart.id = :cartId "
            + "and c.isDeleted = false "
            + "group by c.roomOption.accommodation")
    List<Accommodation> findAllAccommodationByCartId(Long cartId);

    @Query("select c "
        + "from CartProduct c "
        + "where c.cart.id = :cartId "
        + "and c.isDeleted = false ")
    List<CartProduct> findAllByCartId(Long cartId);

}
