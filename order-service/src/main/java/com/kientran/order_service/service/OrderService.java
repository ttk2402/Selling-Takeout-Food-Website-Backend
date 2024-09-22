package com.kientran.order_service.service;

import com.kientran.order_service.dto.OrderDto;
import com.kientran.order_service.dto.ResOrderDto;

import java.util.List;

public interface OrderService {

    ResOrderDto createOrderByStaff(OrderDto orderDto, Integer checkoutId);

    ResOrderDto createOrderByCustomer(OrderDto orderDto, Integer checkoutId, Integer deliveryInfoId);

    ResOrderDto getOrder(Integer orderId);

    void deleteOrder(Integer orderId);

    ResOrderDto changeStatusOfOrder(Integer orderId, Integer orderStatusId);

    List<ResOrderDto> getOrdersByOrderStatus(Integer orderStatusId);

    List<ResOrderDto> getAllOrder();

}
