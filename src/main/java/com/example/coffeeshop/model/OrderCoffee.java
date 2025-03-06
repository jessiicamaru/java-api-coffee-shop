package com.example.coffeeshop.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_coffee")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCoffee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", nullable = false)
    private UserOrder userOrder;

    @ManyToOne
    @JoinColumn(name = "coffee_id", referencedColumnName = "coffee_id", nullable = false)
    private Coffee coffee;

    @Column(name = "size", nullable = false)
    private String size;

    @Column(name = "quantity", nullable = false)
    private int quantity;
}
