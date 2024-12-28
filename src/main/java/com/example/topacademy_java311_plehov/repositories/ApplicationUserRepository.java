package com.example.topacademy_java311_plehov.repositories;

import com.example.topacademy_java311_plehov.model.secuirty.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Integer> {
    Optional<ApplicationUser> findApplicationUserByProfileEmail(String email);
}
