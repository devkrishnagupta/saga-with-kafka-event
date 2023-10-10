package com.example.sagaOrder.repository;

import com.example.sagaOrder.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
