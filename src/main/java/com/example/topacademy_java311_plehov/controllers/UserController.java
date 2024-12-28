package com.example.topacademy_java311_plehov.controllers;

import com.example.topacademy_java311_plehov.model.secuirty.ApplicationUser;
import com.example.topacademy_java311_plehov.repositories.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {
    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserRepository applicationUserRepository;

    @PostMapping("/registration")
    public String registration(@RequestParam String email,@RequestParam String password, @RequestParam String address, @RequestParam String userName){
        ApplicationUser applicationUser = new ApplicationUser(email,passwordEncoder.encode(password), address, userName);
        applicationUserRepository.save(applicationUser);
        return "redirect:/";
    }
}
