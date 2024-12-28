package com.example.topacademy_java311_plehov.repositories;


import com.example.topacademy_java311_plehov.model.shop.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "SELECT * FROM order_t WHERE profile_id = ?1 AND status = 'CART' LIMIT 1;", nativeQuery = true)
    Order findCartByUserId(Long profileId);
    @Query(value = "SELECT * FROM order_t WHERE status = 'IS_PAID';", nativeQuery = true)
    List<Order> ordersToDeliver();
}