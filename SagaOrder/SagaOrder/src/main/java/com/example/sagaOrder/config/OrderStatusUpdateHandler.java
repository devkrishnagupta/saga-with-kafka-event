package com.example.sagaOrder.config;

import com.example.sagaOrder.common.OrderStatus;
import com.example.sagaOrder.common.PaymentStatus;
import com.example.sagaOrder.dtos.OrderRequestDto;
import com.example.sagaOrder.entities.Order;
import com.example.sagaOrder.repository.OrderRepository;
import com.example.sagaOrder.service.OrderPublisher;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.transaction.Transactional;
import java.util.function.Consumer;

@Configuration
public class OrderStatusUpdateHandler {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderPublisher orderPublisher;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public void updateOrder(int id, Consumer<Order> consumer){
        orderRepository.findById(id)
                .ifPresent(consumer.andThen(this::updateOrder));
    }

    private void updateOrder(Order order) {
        boolean isPaymentComplete = PaymentStatus.PAYMENT_COMPLETED.equals(order.getPaymentStatus());
        OrderStatus orderStatus = isPaymentComplete ? OrderStatus.ORDER_COMPLETED : OrderStatus.ORDER_CANCELLED;
        order.setOrderStatus(orderStatus);
        if (!isPaymentComplete){
            OrderRequestDto orderRequestDto = modelMapper.map(order, OrderRequestDto.class);
            orderPublisher.publishOrderEvent(orderRequestDto, orderStatus);
        }
    }
}
