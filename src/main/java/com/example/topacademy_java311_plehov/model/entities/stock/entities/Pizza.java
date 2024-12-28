package com.example.topacademy_java311_plehov.model.entities.stock.entities;



import com.example.topacademy_java311_plehov.model.BaseEntity;
import com.example.topacademy_java311_plehov.model.entities.itemAttributes.Size;
import com.example.topacademy_java311_plehov.model.entities.itemAttributes.Type;
import com.example.topacademy_java311_plehov.model.shop.OrderPosition;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@Table(name = "pizza_t")
@AllArgsConstructor
public class Pizza extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private int price;

    @Column(name = "size")
    @Enumerated(EnumType.STRING)
    private Size size;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type type;


    @ManyToOne
    private OrderPosition orderPosition;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pizza_id")
    private Set<TechCart> techCart;

    public Pizza() {
        techCart = new HashSet<>();
    }
}

