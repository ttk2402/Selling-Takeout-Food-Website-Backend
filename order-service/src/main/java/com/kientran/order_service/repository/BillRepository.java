package com.kientran.order_service.repository;

import com.kientran.order_service.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, Integer> {

}
