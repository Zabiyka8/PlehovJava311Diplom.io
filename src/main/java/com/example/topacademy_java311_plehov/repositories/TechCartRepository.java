package com.example.topacademy_java311_plehov.repositories;

import com.example.topacademy_java311_plehov.model.entities.stock.entities.TechCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechCartRepository extends JpaRepository<TechCart, Integer> {
    @Query(value = "SELECT ingredient_t.name FROM teach_cart_t JOIN ingredient_t on teach_cart_t.ingredient_id = ingredient_t.id WHERE pizza_id = ?1;", nativeQuery = true)
    List<String> findTechCartByPizzaId(Long pizzaId);
}