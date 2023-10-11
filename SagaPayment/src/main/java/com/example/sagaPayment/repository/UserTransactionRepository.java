package com.example.sagaPayment.repository;

import com.example.sagaPayment.entities.UserTransaction;
import com.example.sagaPayment.entities.UserTransactionKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTransactionRepository extends JpaRepository<UserTransaction, Integer> {

}
