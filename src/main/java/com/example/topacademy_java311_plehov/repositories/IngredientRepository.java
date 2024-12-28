package com.example.topacademy_java311_plehov.repositories;


import com.example.topacademy_java311_plehov.model.entities.stock.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
//        @Query(value = "SELECT * from ingtedient_t where name = ?1;", nativeQuery = true)
        Optional<Ingredient> findByName(String name);
}