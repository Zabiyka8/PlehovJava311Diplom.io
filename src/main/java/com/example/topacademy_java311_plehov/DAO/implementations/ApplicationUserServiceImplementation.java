package com.example.topacademy_java311_plehov.DAO.implementations;


import com.example.topacademy_java311_plehov.DAO.services.ApplicationUserService;
import com.example.topacademy_java311_plehov.model.secuirty.ApplicationUser;
import com.example.topacademy_java311_plehov.repositories.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationUserServiceImplementation implements ApplicationUserService {
    private final ApplicationUserRepository repo;


    @Override
    public List<ApplicationUser> findAll() {
        return null;
    }

    @Override
    public Optional<ApplicationUser> findById(int id) {
        return Optional.empty();
    }

    @Override
    public ApplicationUser save(ApplicationUser applicationUser) {
        return null;
    }

    @Override
    public void deleteById(int id) {
        repo.deleteById(id);
    }

    @Override
    public ApplicationUser loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<ApplicationUser> loadedUser = repo.findApplicationUserByProfileEmail(email);
        if (loadedUser.isPresent()) {
            return loadedUser.get();
        } else {
            throw new UsernameNotFoundException("Данный email не зарегистрирован: " + email);
        }
    }
}
