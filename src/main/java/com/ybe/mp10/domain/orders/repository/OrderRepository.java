package com.ybe.mp10.domain.orders.repository;

import com.ybe.mp10.domain.orders.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
