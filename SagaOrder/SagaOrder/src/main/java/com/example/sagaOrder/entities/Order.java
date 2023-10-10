package com.example.sagaOrder.entities;

import com.example.sagaOrder.common.OrderStatus;
import com.example.sagaOrder.common.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "order")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer userId;
    private Integer productId;
    private String price;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
}
