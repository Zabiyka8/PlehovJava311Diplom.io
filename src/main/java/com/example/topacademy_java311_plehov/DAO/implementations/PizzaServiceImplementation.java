package com.example.topacademy_java311_plehov.DAO.implementations;

import com.example.topacademy_java311_plehov.DAO.services.PizzaService;
import com.example.topacademy_java311_plehov.model.entities.stock.entities.Pizza;
import com.example.topacademy_java311_plehov.repositories.PizzaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PizzaServiceImplementation implements PizzaService {
    private final PizzaRepository repo;


    @Override
    public List<Pizza> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Pizza> findById(int id) {
        return repo.findById(id);
    }

    @Override
    public Pizza save(Pizza pizza) {
        return repo.save(pizza);
    }

    @Override
    public void deleteById(int id) {
        repo.deleteById(id);
    }
}
