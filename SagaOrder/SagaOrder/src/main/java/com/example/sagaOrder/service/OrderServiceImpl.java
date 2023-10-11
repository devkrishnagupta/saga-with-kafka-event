package com.example.sagaOrder.service;

import com.example.sagaOrder.common.OrderStatus;
import com.example.sagaOrder.dtos.OrderRequestDto;
import com.example.sagaOrder.entities.Order;
import com.example.sagaOrder.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
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
        //Order orderOld = modelMapper.map(orderRequestDto, Order.class);
        Order orderNew = orderRepository.save(convertDtoToEntity(orderRequestDto));
        orderRequestDto.setOrderId(orderNew.getId());
        //Produce Kafka Event with Order Produce Event
        orderPublisher.publishOrderEvent(orderRequestDto, OrderStatus.ORDER_CREATED);
        log.info("PUBLISH THE EVENT FOR ORDER IN SERVICE orderRequestDto: "+orderRequestDto);
        return orderNew;
    }

    private Order convertDtoToEntity(OrderRequestDto orderRequestDto) {
        Order order = new Order();
        order.setProductId(orderRequestDto.getProductId());
        order.setUserId(orderRequestDto.getUserId());
        order.setOrderStatus(OrderStatus.ORDER_CREATED);
        order.setPrice(orderRequestDto.getAmount());
        return order;
    }

    @Override
    public List<Order> getAllOrder(){
        return orderRepository.findAll();
    }
}
