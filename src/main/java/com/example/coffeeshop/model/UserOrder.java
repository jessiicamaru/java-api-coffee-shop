package com.example.coffeeshop.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "user_order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOrder {
    @Id
    @Column(name = "order_id", nullable = false, updatable = false)
    private String orderId = UUID.randomUUID().toString();

    @ManyToOne
    @JoinColumn(name = "uid", referencedColumnName = "uid")
    private User user;
}
