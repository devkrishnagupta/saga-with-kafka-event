package com.example.sagaOrder.service;

import com.example.sagaOrder.common.OrderStatus;
import com.example.sagaOrder.dtos.OrderRequestDto;
import com.example.sagaOrder.event.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
@Slf4j
public class OrderPublisher {

    @Autowired
    private Sinks.Many<OrderEvent> orderSinks;

    public void publishOrderEvent(OrderRequestDto orderRequestDto, OrderStatus orderStatus){
        OrderEvent orderEvent = new OrderEvent(orderRequestDto, orderStatus);
        orderSinks.tryEmitNext(orderEvent);
//        log.info("OrderPublisher:- EMITTED RESULT "+x+" AND is success "+x.isSuccess());
    }
}
