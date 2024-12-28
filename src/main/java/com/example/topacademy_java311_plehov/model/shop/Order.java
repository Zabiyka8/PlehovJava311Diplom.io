package com.example.topacademy_java311_plehov.model.shop;

import com.example.topacademy_java311_plehov.model.BaseEntity;
import com.example.topacademy_java311_plehov.model.entities.itemAttributes.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@Table(name = "order_t")
@AllArgsConstructor
public class Order extends BaseEntity {
    @Column(name = "price")
    private Double price;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Set<OrderPosition> orderPositions;

    @ManyToOne
    private Profile profile;

    public Order() {
        orderPositions = new HashSet<>();
    }

    public double getPrice() {
        return orderPositions.stream()
                .mapToDouble(orderPosition -> orderPosition.getPizza().getPrice() * orderPosition.getAmount()).sum();
    }
}
