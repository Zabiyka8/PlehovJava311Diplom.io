package com.example.topacademy_java311_plehov.repositories;


import com.example.topacademy_java311_plehov.model.entities.stock.entities.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Integer> {
//    @Query(value = "SELECT * from pizza_t where name = ?1;", nativeQuery = true)
    Optional<Pizza> findByName(String name);
}