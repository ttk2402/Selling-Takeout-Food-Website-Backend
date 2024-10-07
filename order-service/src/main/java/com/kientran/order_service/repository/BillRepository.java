package com.kientran.order_service.repository;

import com.kientran.order_service.entity.Bill;
import com.kientran.order_service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Integer> {

    Bill findByOrder(Order order);
}
