package com.example.sagaOrder.event;

import com.example.sagaOrder.common.PaymentStatus;
import com.example.sagaOrder.dtos.PaymentRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEvent implements Event{
    private UUID eventId=UUID.randomUUID();
    private Date date=new Date();
    private PaymentRequestDto paymentRequestDto;
    private PaymentStatus paymentStatus;

    public PaymentEvent(PaymentRequestDto paymentRequestDto, PaymentStatus paymentStatus){
        this.paymentRequestDto=paymentRequestDto;
        this.paymentStatus=paymentStatus;
    }
}
