package com.example.topacademy_java311_plehov.DAO.services;

import com.example.topacademy_java311_plehov.model.secuirty.ApplicationUser;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface ApplicationUserService extends DAO<ApplicationUser> {
    ApplicationUser loadUserByUsername(String email) throws UsernameNotFoundException;
}
