package com.kientran.order_service.controller;

import com.kientran.order_service.dto.OrderDto;
import com.kientran.order_service.dto.ResOrderDto;
import com.kientran.order_service.response.ApiResponse;
import com.kientran.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/add/{checkoutId}")
    public ResponseEntity<ResOrderDto> addOrderByStaff(@RequestBody OrderDto orderDto,
                                                   @PathVariable Integer checkoutId) {
        ResOrderDto createOrder = this.orderService.createOrderByStaff(orderDto, checkoutId);
        return new ResponseEntity<ResOrderDto>(createOrder, HttpStatus.CREATED);
    }

    @PostMapping("/add/{checkoutId}/{deliveryInfoId}")
    public ResponseEntity<ResOrderDto> addOrderByCustomer(@RequestBody OrderDto orderDto,
                                                @PathVariable Integer checkoutId,
                                                @PathVariable Integer deliveryInfoId) {
        ResOrderDto createOrder = this.orderService.createOrderByCustomer(orderDto, checkoutId, deliveryInfoId);
        return new ResponseEntity<ResOrderDto>(createOrder, HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ResOrderDto> getOrder(@PathVariable Integer orderId) {
        ResOrderDto orderDto = this.orderService.getOrder(orderId);
        return new ResponseEntity<ResOrderDto>(orderDto, HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponse> deleteOrderById(@PathVariable Integer orderId) {
        this.orderService.deleteOrder(orderId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Order is deleted successfully!", true),
                HttpStatus.OK);
    }

    @PutMapping("/{orderId}/{orderStatusId}")
    public ResponseEntity<ResOrderDto> changeOrderStatusById(@PathVariable Integer orderId,
                                                             @PathVariable Integer orderStatusId
    ) {
        ResOrderDto updatedOr = this.orderService.changeStatusOfOrder(orderId, orderStatusId);
        return new ResponseEntity<ResOrderDto>(updatedOr, HttpStatus.OK);
    }

    @GetMapping("/orderStatus/{orderStatusId}")
    public ResponseEntity<List<ResOrderDto>> getOrdersByOrderStatusID(@PathVariable Integer orderStatusId) {
        List<ResOrderDto> orderDtos = this.orderService.getOrdersByOrderStatus(orderStatusId);
        return new ResponseEntity<List<ResOrderDto>>(orderDtos, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<ResOrderDto>> getAllOrder() {
        List<ResOrderDto> orderDtos = this.orderService.getAllOrder();
        return new ResponseEntity<List<ResOrderDto>>(orderDtos, HttpStatus.OK);
    }

}