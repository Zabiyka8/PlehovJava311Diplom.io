package com.example.topacademy_java311_plehov.DAO.services;

import com.example.topacademy_java311_plehov.model.entities.stock.entities.Ingredient;


public interface IngredientService extends DAO<Ingredient> {
    Ingredient findByName(String name);
    void remove(Ingredient ingredient, int amount);
}
