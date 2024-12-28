package com.example.topacademy_java311_plehov.controllers;

import com.example.topacademy_java311_plehov.DAO.services.ApplicationUserService;
import com.example.topacademy_java311_plehov.DAO.services.OrderPositionService;
import com.example.topacademy_java311_plehov.DAO.services.OrderService;
import com.example.topacademy_java311_plehov.model.entities.itemAttributes.Status;
import com.example.topacademy_java311_plehov.model.entities.stock.entities.TechCart;
import com.example.topacademy_java311_plehov.model.entities.stock.entities.Topping;
import com.example.topacademy_java311_plehov.model.secuirty.ApplicationUser;
import com.example.topacademy_java311_plehov.model.shop.Order;
import com.example.topacademy_java311_plehov.model.shop.OrderPosition;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final OrderService orderService;
    private final ApplicationUserService applicationUserservice;
    private final OrderPositionService orderPositionService;

    @GetMapping
    public String cartPage(Model model) {
        ApplicationUser currentUser = currentApplicationUser();
        Order cart = currentUserCart(currentUser);

        model.addAttribute("cart", cart); // Добавлено: добавляем корзину в модель для шаблона
        return "ui/pages/cart";
    }
    @PostMapping("/add")
    public String addToCart(@RequestParam int pizzaId, RedirectAttributes ra) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            orderService.addToCart(email, pizzaId);
            ra.addFlashAttribute("success", "Пицца добавлена в корзину!");
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/";
        }
    }

    @PostMapping("/delete")
    public String deletePosition(@RequestParam int positionId) {
        orderPositionService.deleteById(positionId);
        return "redirect:/cart";
    }

    @PostMapping("/drop")
    public String deleteOneFromOrderPosition(@RequestParam int positionId) {
        OrderPosition orderPositionToUpdate = orderPositionService.findById(positionId).get();
        orderPositionToUpdate.setAmount(orderPositionToUpdate.getAmount() - 1);
        if (orderPositionToUpdate.getAmount() == 0) {
            orderPositionService.deleteById(positionId);
        } else {
            orderPositionService.save(orderPositionToUpdate);
        }
        return "redirect:/cart";
    }

    @PostMapping("/pay")
    public String pay(RedirectAttributes ra) {
        ApplicationUser currentUser = currentApplicationUser();
        Order cart = currentUserCart(currentUser);
        double price = cart.getPrice();
        // Выясняем, есть ли возможность собрать заказ
        if (canBeDelivered(cart)) {
            if (currentUser.getProfile().pay(price)) {
                orderService.pay(cart);
                return "redirect:/";
            } else {
                ra.addFlashAttribute("payError", "заказ не был оплачен");
                return "redirect:/cart";
            }
        } else {
            ra.addFlashAttribute("deliveryError", "заказ невозможно собрать - часть товара нет в наличии");
            return "redirect:/cart";
        }
    }

    @PostMapping("/match")
    public String match() {
        Order cart = currentUserCart(currentApplicationUser());
        cart.getOrderPositions().forEach(p -> matchPosition(p));
        return "redirect:/cart";
    }

    private void matchPosition(OrderPosition position) {
        if (canIngredientsBeDelivered(position)) {
            orderPositionService.save(position);
        }
    }

    private Order currentUserCart(ApplicationUser currentUser) {
        return currentUser.getProfile().getOrders().stream()
                .filter(o -> o.getStatus().equals(Status.CART))
                .findFirst()
                .orElseGet(() -> {
                    Order order = new Order();
                    order.setOrderPositions(new HashSet<>());
                    return order;
                });
    }

    private ApplicationUser currentApplicationUser() {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        return applicationUserservice.loadUserByUsername(email);
    }

    private boolean canBeDelivered(Order cart) {
        AtomicBoolean canBeDelivered = new AtomicBoolean(true);
        cart.getOrderPositions().forEach(orderPosition -> {
            if (!canIngredientsBeDelivered(orderPosition)) {
                canBeDelivered.set(false);
            }
        });
        return canBeDelivered.get();
    }

    private boolean canIngredientsBeDelivered(OrderPosition orderPosition) {
        // Получаем все ингредиенты для текущей позиции заказа
        Set<Topping> toppings = orderPosition.getToppings();
        Set<TechCart> techCarts = orderPosition.getPizza().getTechCart();
        for (TechCart techCart : techCarts) {
            // Проверяем, хватает ли ингредиента на складе
            if (techCart.getIngredient().getAmount() * orderPosition.getAmount() > techCart.getIngredient().getInStock()) {
                return false; // Если недостаточно, возвращаем false
            }
        }
        for (Topping topping : toppings) {
            // Проверяем, хватает ли ингредиента на складе
            if (topping.getIngredient().getAmount() * orderPosition.getAmount() > topping.getIngredient().getInStock()) {
                return false; // Если недостаточно, возвращаем false
            }
        }
        return true; // Все ингредиенты в наличии
    }
}

