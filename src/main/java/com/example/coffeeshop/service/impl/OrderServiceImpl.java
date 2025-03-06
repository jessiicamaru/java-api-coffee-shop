package com.example.coffeeshop.service.impl;

import com.example.coffeeshop.dto.OrderRequest;
import com.example.coffeeshop.model.Coffee;
import com.example.coffeeshop.model.OrderCoffee;
import com.example.coffeeshop.model.User;
import com.example.coffeeshop.model.UserOrder;
import com.example.coffeeshop.repository.CoffeeRepository;
import com.example.coffeeshop.repository.OrderCoffeeRepository;
import com.example.coffeeshop.repository.UserOrderRepository;
import com.example.coffeeshop.repository.UserRepository;
import com.example.coffeeshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private UserOrderRepository userOrderRepository;

    @Autowired
    private OrderCoffeeRepository orderCoffeeRepository;

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Autowired
    private UserRepository userRepository;

    public void createOrder(OrderRequest orderRequest) {
        String orderId = UUID.randomUUID().toString();

        User user = userRepository.findByUid(orderRequest.getUid());

        UserOrder userOrder = new UserOrder(orderId, user);
        userOrderRepository.save(userOrder);

        List<OrderCoffee> coffeeOrders = orderRequest.getCoffees().stream().map(coffeeItem -> {
            Coffee coffee = coffeeRepository.findByCoffeeId(coffeeItem.getCoffeeId());

            return OrderCoffee.builder()
                    .userOrder(userOrder)
                    .coffee(coffee)
                    .quantity(coffeeItem.getQuantity())
                    .size(coffeeItem.getSize())
                    .build();
        }).toList();

        orderCoffeeRepository.saveAll(coffeeOrders);
    }
}
