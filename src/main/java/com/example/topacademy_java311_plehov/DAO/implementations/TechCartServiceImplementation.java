package com.example.topacademy_java311_plehov.DAO.implementations;

import com.example.topacademy_java311_plehov.DAO.services.TechCartService;
import com.example.topacademy_java311_plehov.model.entities.stock.entities.TechCart;
import com.example.topacademy_java311_plehov.repositories.TechCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TechCartServiceImplementation implements TechCartService {
    private final TechCartRepository repo;

    @Override
    public List<TechCart> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<TechCart> findById(int id) {
        return repo.findById(id);
    }

    @Override
    public TechCart save(TechCart techCart) {
        return repo.save(techCart);
    }

    @Override
    public void deleteById(int id) {
        repo.deleteById(id);
    }

    @Override
    public List<String> findTechCartByPizzaId(Long pizzaId) {
        return repo.findTechCartByPizzaId(pizzaId);
    }

}
