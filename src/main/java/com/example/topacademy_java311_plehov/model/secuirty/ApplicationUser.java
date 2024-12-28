package com.example.topacademy_java311_plehov.model.secuirty;


import com.example.topacademy_java311_plehov.model.BaseEntity;
import com.example.topacademy_java311_plehov.model.entities.itemAttributes.Status;
import com.example.topacademy_java311_plehov.model.shop.Order;
import com.example.topacademy_java311_plehov.model.shop.Profile;
import jakarta.persistence.*;
import lombok.*;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.HashSet;

import static com.example.topacademy_java311_plehov.model.secuirty.Role.ROLE_USER;


@Getter
@Setter
@Builder
@Entity
@Table(name = "application_user_t")
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationUser extends BaseEntity {
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Profile profile;

    public ApplicationUser(String email, String password, String address, String userName) {
        this.username = "";
        this.password = password;
        this.role = ROLE_USER;
        this.profile = Profile.builder()
                .user(this)
                .email(email)
                .address(address)
                .name(userName)
                .orders(new HashSet<>() {{
                    add(Order.builder()
                            .status(Status.CART)
                            .orderPositions(new HashSet<>())
                            .build());
                }})
                .build();
    }

    public UserDetails securityUserFromEntity() {
        return new User(
                this.profile.getEmail(),
                password,
                true,
                true,
                true,
                true,
                new ArrayList<>(){{add(role);}}
        );
    }
}
