package com.example.topacademy_java311_plehov.DAO.implementations;

import com.example.topacademy_java311_plehov.DAO.services.ToppingService;
import com.example.topacademy_java311_plehov.model.entities.stock.entities.Topping;
import com.example.topacademy_java311_plehov.repositories.ToppingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ToppingServiceImplementation implements ToppingService {
    private final ToppingRepository repo;

    @Override
    public List<Topping> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Topping> findById(int id) {
        return repo.findById(id);
    }

    @Override
    public Topping save(Topping topping) {
        return repo.save(topping);
    }

    @Override
    public void deleteById(int id) {
        repo.deleteById(id);
    }
}
