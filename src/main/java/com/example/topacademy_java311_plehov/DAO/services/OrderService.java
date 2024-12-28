package com.example.topacademy_java311_plehov.DAO.services;

import com.example.topacademy_java311_plehov.model.shop.Order;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;


public interface OrderService extends DAO<Order> {

    Optional<Order> findCartByUserId(Long profileId);
    @Transactional
    void addToCart(String email, int pizzaId);
    Order findByOrderPositionId(int orderPositionId);
    @Transactional
    void pay(Order cart);
    List<Order> ordersToDeliver();
    void deliver(int orderId);

}
