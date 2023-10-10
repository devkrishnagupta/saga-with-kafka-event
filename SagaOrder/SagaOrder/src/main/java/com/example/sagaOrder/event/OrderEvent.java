package com.example.sagaOrder.event;

import com.example.sagaOrder.common.OrderStatus;
import com.example.sagaOrder.dtos.OrderRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent implements Event {
    private UUID eventId=UUID.randomUUID();
    private Date date=new Date();
    private OrderRequestDto orderRequestDto;
    private OrderStatus orderStatus;

    public OrderEvent(OrderRequestDto orderRequestDto, OrderStatus orderStatus){
        this.orderRequestDto=orderRequestDto;
        this.orderStatus=orderStatus;
    }
}
