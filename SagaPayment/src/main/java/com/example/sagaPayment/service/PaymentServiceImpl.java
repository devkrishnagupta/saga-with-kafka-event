package com.example.sagaPayment.service;

import com.example.sagaPayment.common.PaymentStatus;
import com.example.sagaPayment.dtos.OrderRequestDto;
import com.example.sagaPayment.dtos.PaymentRequestDto;
import com.example.sagaPayment.entities.UserBalance;
import com.example.sagaPayment.entities.UserTransaction;
import com.example.sagaPayment.event.OrderEvent;
import com.example.sagaPayment.event.PaymentEvent;
import com.example.sagaPayment.repository.UserBalanceRepository;
import com.example.sagaPayment.repository.UserTransactionRepository;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private UserBalanceRepository userBalanceRepository;

    @Autowired
    private UserTransactionRepository userTransactionRepository;

//    @PostConstruct
    public void init(){
        userBalanceRepository.saveAll(
                Stream.of(
                        new UserBalance(102, 3000),
                        new UserBalance(103, 4200),
                        new UserBalance(104, 20000),
                        new UserBalance(105, 999)
                ).collect(Collectors.toList())
        );
    }
    //get the user id.
    //check the balance availability.
    //if balance sufficient -> Payment completed and deduct amount from DB.
    //if payment not sufficient -> cancel the order event and update the amount in DB.    @Override
    @Override
    @Transactional
    public PaymentEvent newOrderEvent(OrderEvent orderEvent) {
        OrderRequestDto orderRequestDto = orderEvent.getOrderRequestDto();
        PaymentRequestDto paymentRequestDto = new PaymentRequestDto(
                orderRequestDto.getOrderId(),
                orderRequestDto.getUserId(),
                orderRequestDto.getAmount()
        );
        return userBalanceRepository.findById(orderRequestDto.getUserId())
                .filter(ub->ub.getPrice()>orderRequestDto.getAmount())
                .map(ub -> {
                    ub.setPrice(ub.getPrice()-orderRequestDto.getAmount());
                    userTransactionRepository.save(new UserTransaction(
                        orderRequestDto.getOrderId(),
                        orderRequestDto.getUserId(),
                        orderRequestDto.getAmount()
                    ));
                    return new PaymentEvent(paymentRequestDto, PaymentStatus.PAYMENT_COMPLETED);
                }).orElse(new PaymentEvent(paymentRequestDto, PaymentStatus.PAYMENT_FAILED));
    }

    @Override
    @Transactional
    public void cancelOrderEvent(OrderEvent orderEvent) {
        //OrderRequestDto orderRequestDto = orderEvent.getOrderRequestDto();

        userTransactionRepository.findById(orderEvent.getOrderRequestDto().getOrderId())
                .ifPresent(ut -> {
                    userTransactionRepository.delete(ut);
                    userTransactionRepository.findById(orderEvent.getOrderRequestDto().getUserId())
                            .ifPresent(ub -> ub.setAmount(ub.getAmount()+ub.getAmount()));
                });
    }
}
