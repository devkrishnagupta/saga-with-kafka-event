package com.example.sagaPayment.service;

import com.example.sagaPayment.event.OrderEvent;
import com.example.sagaPayment.event.PaymentEvent;

public interface PaymentService {
    PaymentEvent newOrderEvent(OrderEvent orderEvent);

    void cancelOrderEvent(OrderEvent orderEvent);
}
