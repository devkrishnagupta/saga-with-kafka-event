package com.example.sagaPayment.entities;

import javax.persistence.Id;

import java.io.Serializable;

public class UserTransactionKey implements Serializable {
    private Integer orderId;
    private Integer userId;
}
