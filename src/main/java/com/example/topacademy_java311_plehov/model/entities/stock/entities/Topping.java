package com.example.topacademy_java311_plehov.model.entities.stock.entities;

import com.example.topacademy_java311_plehov.model.BaseEntity;
import com.example.topacademy_java311_plehov.model.shop.OrderPosition;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@Table(name = "topping_t")
@AllArgsConstructor
@NoArgsConstructor
public class Topping extends BaseEntity {

    @ManyToOne
    private Ingredient ingredient;

    @ManyToOne
    private OrderPosition orderPosition;
}
