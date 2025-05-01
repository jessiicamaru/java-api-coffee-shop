package com.example.coffeeshop.repository;

import com.example.coffeeshop.model.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserOrderRepository extends JpaRepository<UserOrder, String> {
    @Query("SELECT COUNT(uo) FROM UserOrder uo WHERE uo.user.uid = :uid")
    int countOrdersByUser(String uid);
}
