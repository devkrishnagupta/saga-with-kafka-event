package com.example.sagaOrder.service;

import com.example.sagaOrder.common.OrderStatus;
import com.example.sagaOrder.dtos.OrderRequestDto;
import com.example.sagaOrder.entities.Order;
import com.example.sagaOrder.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrderPublisher orderPublisher;

    @Override
    @Transactional
    public Order create(OrderRequestDto orderRequestDto) {
        Order orderOld = modelMapper.map(orderRequestDto, Order.class);
        Order orderNew = orderRepository.save(orderOld);
        //Produce Kafka Event with Order Produce Event
        orderPublisher.publishOrderEvent(orderRequestDto, OrderStatus.ORDER_CREATED);
        return orderNew;
    }

    @Override
    public List<Order> getAllOrder(){
        return orderRepository.findAll();
    }
}
