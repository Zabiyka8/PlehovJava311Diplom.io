package com.example.topacademy_java311_plehov.controllers;

import com.example.topacademy_java311_plehov.DAO.services.OrderService;
import com.example.topacademy_java311_plehov.model.shop.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {
    private final OrderService orderService;

    @GetMapping
    public String managersPage(Model model) {
        List<Order> ordersToDeliver = orderService.ordersToDeliver();
        model.addAttribute("orders", ordersToDeliver);
        return "ui/pages/managerHomePage";
    }

    @PostMapping("/deliver")
    public String deliver(@RequestParam int orderId) {
        orderService.deliver(orderId);
        return "redirect:/manager";
    }

    @PostMapping("/decline")
    public String decline(@RequestParam int orderId) {
        orderService.deleteById(orderId);
        return "redirect:/manager";
    }
}
