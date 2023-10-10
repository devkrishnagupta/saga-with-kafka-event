package com.example.sagaPayment.repository;

import com.example.sagaPayment.entities.UserTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTransactionRepository extends JpaRepository<UserTransaction, Integer> {

}
