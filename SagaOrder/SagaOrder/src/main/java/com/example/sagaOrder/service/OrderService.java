package com.example.sagaOrder.service;

import com.example.sagaOrder.dtos.OrderRequestDto;
import com.example.sagaOrder.entities.Order;

import java.util.List;

public interface OrderService {
    Order create(OrderRequestDto orderRequestDto);
    List<Order> getAllOrder();
}
