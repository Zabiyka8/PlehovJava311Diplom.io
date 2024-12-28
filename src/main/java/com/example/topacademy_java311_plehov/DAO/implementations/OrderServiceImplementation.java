package com.example.topacademy_java311_plehov.DAO.implementations;

import com.example.topacademy_java311_plehov.DAO.services.*;
import com.example.topacademy_java311_plehov.model.entities.itemAttributes.Status;
import com.example.topacademy_java311_plehov.model.entities.stock.entities.Pizza;
import com.example.topacademy_java311_plehov.model.entities.stock.entities.TechCart;
import com.example.topacademy_java311_plehov.model.entities.stock.entities.Topping;
import com.example.topacademy_java311_plehov.model.secuirty.ApplicationUser;
import com.example.topacademy_java311_plehov.model.shop.Order;
import com.example.topacademy_java311_plehov.model.shop.OrderPosition;
import com.example.topacademy_java311_plehov.model.shop.Profile;
import com.example.topacademy_java311_plehov.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImplementation implements OrderService {
    @Value("${spring.mail.username}")
    private String emailFrom;
    private final OrderRepository repo;
    private final ApplicationUserService applicationUserService;
    private final OrderPositionService orderPositionService;
    private final PizzaService pizzaService;
    private final ProfileService profileService;
    private final IngredientService ingredientService;
    private final JavaMailSender mailSender;

    @Override
    public List<Order> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Order> findById(int id) {
        return repo.findById((long) id);
    }

    @Override
    public Order save(Order order) {
        return repo.save(order);
    }

    @Override
    public void deleteById(int id) {
        repo.deleteById(Long.valueOf(id));
    }

    @Override
    public Optional<Order> findCartByUserId(Long profileId) {
        return Optional.ofNullable(repo.findCartByUserId(profileId));
    }

    @Override
    @Transactional
    public void addToCart(String email, int pizzaId){
        ApplicationUser loggedUser = applicationUserService.loadUserByUsername(email);
        Order cart = findCartByUserId(loggedUser.getProfile().getId())
                .orElseThrow(() -> new IllegalArgumentException("Корзина не найдена"));

        Optional<Pizza> stockPositionToBuy = Optional.ofNullable(pizzaService.findById(pizzaId)
                .orElseThrow(() -> new IllegalArgumentException("Такого товара нет в наличии")));
        OrderPosition positionToAdd = OrderPosition.builder()
                .amount(1)
                .pizza(stockPositionToBuy.get())
                .order(cart)
                .build();
        addToCartPosiyion(cart, positionToAdd);
        System.out.println(cart.getOrderPositions());
        repo.save(cart);
    }

    private void addToCartPosiyion(Order cart, OrderPosition positionToAdd) {
        if (isStockPositionPresent(cart, positionToAdd)) {
            OrderPosition positionToIncrementAmount = cart.getOrderPositions().stream()
                    .filter(orderPosition -> orderPosition.getPizza().getId() == positionToAdd.getPizza().getId())
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Позиция для увеличения не найдена"));
            positionToIncrementAmount.setAmount(positionToIncrementAmount.getAmount() + 1);
        } else {
            cart.getOrderPositions().add(positionToAdd);
        }
    }

    @Override
    public Order findByOrderPositionId(int orderPositionId) {
        return orderPositionService.findById(orderPositionId).orElseThrow(() ->
                new IllegalArgumentException("Позиция заказа не найдена")).getOrder();
    }

    @Override
    @Transactional
    public void pay(Order cart) {
        cart.setStatus(Status.IS_PAID);
        repo.save(cart);
        removeFromStock(cart);
        Profile currentProfile = cart.getProfile();
        Order newCart = Order.builder()
                .status(Status.CART)
                .profile(currentProfile)
                .orderPositions(new HashSet<>())
                .build();
        currentProfile.getOrders().add(newCart);
        profileService.save(currentProfile);
    }

    private void removeFromStock(Order cart) {
        Set<OrderPosition> orderPositionSet = cart.getOrderPositions();
        orderPositionSet.forEach(orderPosition -> {
            Set<Topping> toppings = orderPosition.getToppings();
            Set<TechCart> techCarts = orderPosition.getPizza().getTechCart();
            techCarts.forEach(techCart ->
                    ingredientService.remove(techCart.getIngredient(), techCart.getIngredient().getAmount() * orderPosition.getAmount())
            );
            toppings.forEach(topping ->
                    ingredientService.remove(topping.getIngredient(), topping.getIngredient().getAmount() * orderPosition.getAmount())
            );
        });
    }

    @Override
    public List<Order> ordersToDeliver() {
        return repo.findAll().stream()
                .filter(order -> order.getStatus().equals(Status.IS_PAID))
                .collect(Collectors.toList());
    }

    @Override
    public void deliver(int orderId) {
        Order orderToDeliver = repo.findById((long) orderId).get();
        orderToDeliver.setStatus(Status.IS_DELIVERED);
        repo.save(orderToDeliver);
        String subject = "Заказ оплачен";
        String messageBody =
                "Вас беспокоит Simple House, " +
                "/nЗаказ оплачен и будет доставлен по адресу: " + orderToDeliver.getProfile().getAddress();
        String emailTo = orderToDeliver.getProfile().getEmail();
        sendDeliveryEmail(subject, emailTo, messageBody);
    }
    private void sendDeliveryEmail(String subject, String emailTo, String messageBody) {
        SimpleMailMessage message = createMailMessage(subject, emailTo, messageBody);
        mailSender.send(message);
    }
    private SimpleMailMessage createMailMessage(String subject, String emailTo, String messageBody) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(messageBody);
        mailMessage.setFrom(emailFrom);
        return mailMessage;
    }
    private boolean isStockPositionPresent(Order cart, OrderPosition positionToAdd) {
        return cart.getOrderPositions().stream()
                .anyMatch(orderPosition -> orderPosition.getPizza().getId() == positionToAdd.getPizza().getId());
    }
}
