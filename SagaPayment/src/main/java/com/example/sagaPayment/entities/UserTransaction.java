package com.example.sagaPayment.entities;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transactions")
@Data
//@IdClass(UserTransactionKey.class)
@AllArgsConstructor
@NoArgsConstructor
public class UserTransaction {

    @Id
    private Integer orderId;
    private Integer userId;
    private Integer amount;
}
