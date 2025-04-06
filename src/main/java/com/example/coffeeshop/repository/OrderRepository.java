package com.example.coffeeshop.repository;

import com.example.coffeeshop.dto.OrderResponse;
import com.example.coffeeshop.model.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<UserOrder, String> {
    @Query("""
            SELECT
                o.orderId, u.displayName, u.email,
                c.coffeeTitle, c.coffeePhotoUrl, c.coffeeCost,
                cat.categoryTitle, oc.quantity, oc.size, o.address,\s
                o.stat, o.total, o.fee, o.longitude, o.latitude, o.note,
                o.createdAt\s
            FROM UserOrder o
            JOIN o.user u
            JOIN OrderCoffee oc ON o.orderId = oc.userOrder.orderId
            JOIN oc.coffee c
            JOIN c.category cat
            ORDER BY o.createdAt DESC
           \s""")
    List<Object[]> findAllOrders();

    @Query("""
            SELECT
                o.orderId, u.displayName, u.email,
                c.coffeeTitle, c.coffeePhotoUrl, c.coffeeCost,
                cat.categoryTitle, oc.quantity, oc.size, o.address,\s
                o.stat, o.total, o.fee, o.longitude, o.latitude, o.note,
                o.createdAt\s
            FROM UserOrder o
            JOIN o.user u
            JOIN OrderCoffee oc ON o.orderId = oc.userOrder.orderId
            JOIN oc.coffee c
            JOIN c.category cat
            WHERE o.user.uid = :uid
            ORDER BY o.createdAt ASC
           \s""")
    List<Object[]> findAllOrdersByUid(String uid);
}
