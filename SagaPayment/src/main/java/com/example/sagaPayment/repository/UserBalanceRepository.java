package com.example.sagaPayment.repository;

import com.example.sagaPayment.entities.UserBalance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBalanceRepository extends JpaRepository<UserBalance, Integer> {
}
