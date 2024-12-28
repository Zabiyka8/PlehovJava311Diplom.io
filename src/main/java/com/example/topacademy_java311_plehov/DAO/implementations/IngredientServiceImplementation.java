package com.example.topacademy_java311_plehov.DAO.implementations;

import com.example.topacademy_java311_plehov.DAO.services.IngredientService;
import com.example.topacademy_java311_plehov.model.entities.stock.entities.Ingredient;
import com.example.topacademy_java311_plehov.repositories.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class IngredientServiceImplementation implements IngredientService {
    private final IngredientRepository repo;


    @Override
    public List<Ingredient> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Ingredient> findById(int id) {
        return repo.findById(id);
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        return repo.save(ingredient);
    }

    @Override
    public void deleteById(int id) {
        repo.deleteById(id);
    }
    @Override
    public Ingredient findByName(String name) {
        Optional<Ingredient> ingredientOptional = repo.findByName(name);
        return ingredientOptional.orElse(null);
    }

    @Override
    public void remove(Ingredient ingredient, int amount) {
        ingredient.setInStock(ingredient.getInStock() - amount);
        repo.save(ingredient);
    }
}
