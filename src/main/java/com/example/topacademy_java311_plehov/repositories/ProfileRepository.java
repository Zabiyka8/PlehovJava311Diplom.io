package com.example.topacademy_java311_plehov.repositories;


import com.example.topacademy_java311_plehov.model.shop.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {
}
