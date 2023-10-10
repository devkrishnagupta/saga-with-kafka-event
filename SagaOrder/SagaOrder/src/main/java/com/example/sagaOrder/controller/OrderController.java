package com.example.sagaOrder.controller;

import com.example.sagaOrder.dtos.OrderRequestDto;
import com.example.sagaOrder.entities.Order;
import com.example.sagaOrder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Order createOrder(OrderRequestDto orderRequestDto){
        return this.orderService.create(orderRequestDto);
    }

    @GetMapping
    public List<Order> getAllOrder(){
        return orderService.getAllOrder();
    }
}
