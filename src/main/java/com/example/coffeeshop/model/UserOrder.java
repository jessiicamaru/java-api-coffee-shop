package com.example.coffeeshop.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
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
    @JoinColumn(name = "uid", referencedColumnName = "uid", nullable = false)
    private User user;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private LocalDateTime createdAt;

    public UserOrder(String orderId, User user) {
        this.orderId = orderId;
        this.user = user;
    }
}
