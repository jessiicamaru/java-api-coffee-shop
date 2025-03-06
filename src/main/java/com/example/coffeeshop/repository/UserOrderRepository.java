package com.example.coffeeshop.repository;

import com.example.coffeeshop.model.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOrderRepository extends JpaRepository<UserOrder, String> {
}
