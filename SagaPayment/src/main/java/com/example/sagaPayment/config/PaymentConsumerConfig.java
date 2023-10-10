package com.example.sagaPayment.config;

import com.example.sagaPayment.common.OrderStatus;
import com.example.sagaPayment.event.OrderEvent;
import com.example.sagaPayment.event.PaymentEvent;
import com.example.sagaPayment.service.PaymentService;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Configuration
public class PaymentConsumerConfig {

    @Autowired
    private PaymentService paymentService;

    @Bean
    public Function<Flux<OrderEvent>, Flux<PaymentEvent>> paymentProcessor(){
        return orderEventFlux -> orderEventFlux.flatMap(this::processPayment);
    }

    private Mono<PaymentEvent> processPayment(OrderEvent orderEvent) {
        if(OrderStatus.ORDER_CREATED.equals(orderEvent.getOrderStatus())){
            return Mono.fromSupplier(()->this.paymentService.newOrderEvent(orderEvent));
        }else {
            return Mono.fromRunnable(()->this.paymentService.cancelOrderEvent(orderEvent));
        }
    }
}
