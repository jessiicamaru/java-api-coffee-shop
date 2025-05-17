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

    private String address;
    private int stat;
    private Double total;
    private Double fee;
    private Double longitude;
    private Double latitude;
    private String note;
    private Double originalTotal;
    private Double originalFee;
    private String receiveCustomer;

    public UserOrder(String orderId, User user, String address, int stat, Double total, Double fee, Double originalTotal, Double originalFee, Double longitude, Double latitude, String note, String receiveCustomer) {
        this.orderId = orderId;
        this.user = user;
        this.address = address;
        this.stat = stat;
        this.total = total;
        this.fee = fee;
        this.latitude = latitude;
        this.longitude = longitude;
        this.note = note;
        this.originalTotal = originalTotal;
        this.originalFee = originalFee;
        this.receiveCustomer = receiveCustomer;
    }
}
