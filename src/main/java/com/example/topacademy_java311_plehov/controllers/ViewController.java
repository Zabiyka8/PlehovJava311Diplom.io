package com.example.topacademy_java311_plehov.controllers;

import com.example.topacademy_java311_plehov.DAO.services.PizzaService;
import com.example.topacademy_java311_plehov.DAO.services.TechCartService;
import com.example.topacademy_java311_plehov.model.entities.stock.DTO.PizzaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class ViewController {

    private final PizzaService pizzaService;
    private final TechCartService techCartService;

    @GetMapping
    public String index(Model model, RedirectAttributes ra){
        List<PizzaDTO> pizzas = pizzaService.findAll().stream()
                .map(pizza -> PizzaDTO.builder()
                        .price(pizza.getPrice())
                        .id(pizza.getId())
                        .name(pizza.getName())
                        .techCart(listToString(techCartService.findTechCartByPizzaId(pizza.getId())))
                        .build())
                .toList();
        ra.addFlashAttribute("payError", "Пица добавлена в корзину");
        model.addAttribute("pizzas", pizzas);
        return "ui/pages/index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "ui/pages/login";
    }
    @GetMapping("/registration")
    public String registrationPage() {
        return "ui/pages/registration";
    }

    public String listToString(List<String> list){
        return String.join(", ", list);
    }
}
