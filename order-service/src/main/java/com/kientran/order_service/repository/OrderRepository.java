package com.kientran.order_service.repository;

import com.kientran.order_service.entity.Order;
import com.kientran.order_service.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByOrderStatus(OrderStatus orderStatus);

}
