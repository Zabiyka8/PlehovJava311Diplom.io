package com.example.topacademy_java311_plehov.model.entities.stock.DTO;

import com.example.topacademy_java311_plehov.model.entities.stock.entities.TechCart;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PizzaDTO {
    private Long id;
    private String techCart;
    private String name;
    private int price;
}