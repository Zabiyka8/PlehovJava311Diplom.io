package com.example.topacademy_java311_plehov.repositories;


import com.example.topacademy_java311_plehov.model.shop.OrderPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderPositionRepository extends JpaRepository<OrderPosition, Integer> {

}