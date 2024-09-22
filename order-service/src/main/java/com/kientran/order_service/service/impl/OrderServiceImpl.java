package com.kientran.order_service.service.impl;

import com.kientran.order_service.client.CartServiceClient;
import com.kientran.order_service.client.ResItemDto;
import com.kientran.order_service.dto.BillDto;
import com.kientran.order_service.dto.ItemOrderedDto;
import com.kientran.order_service.dto.OrderDto;
import com.kientran.order_service.dto.ResOrderDto;
import com.kientran.order_service.entity.*;
import com.kientran.order_service.exception.ResourceNotFoundException;
import com.kientran.order_service.repository.*;
import com.kientran.order_service.service.BillService;
import com.kientran.order_service.service.ItemOrderedService;
import com.kientran.order_service.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private DeliveryInformationRepository deliveryInfoRepository;

    @Autowired
    private CheckoutRepository checkOutRepository;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ItemOrderedService itemOrderedService;

    @Autowired
    private BillService billService;

    @Autowired
    CartServiceClient cartServiceClient;

    @Override
    public ResOrderDto createOrderByStaff(OrderDto orderDto, Integer checkoutId) {
        Checkout checkout = this.checkOutRepository.findById(checkoutId).orElseThrow(()-> new ResourceNotFoundException("CheckOut","CheckOutId", checkoutId));
        OrderStatus orStatus = this.orderStatusRepository.findOrderStatusByStatus("Shipping");
        Order order = this.modelMapper.map(orderDto, Order.class);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = now.format(formatter);
        order.setDate(formattedDate);

        order.setCheckout(checkout);
        order.setOrderStatus(orStatus);
        Order addOrder = this.orderRepository.save(order);

        List<ResItemDto> items = this.cartServiceClient.getItemByAccountID(orderDto.getAccountId());
        Double total_price = 0.0;
        for (ResItemDto itemDto : items) {
            ItemOrderedDto ordered = modelMapper.map(itemDto, ItemOrderedDto.class);
            this.itemOrderedService.createItem(ordered, addOrder.getId());
            total_price += itemDto.getPrice()*itemDto.getQuantity();
            this.cartServiceClient.deleteItem(itemDto.getId());
        }
        Integer orderId = addOrder.getId();
        Order orderUpdate = this.orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order","OrderId", orderId));
        orderUpdate.setTotalprice(total_price);

        BillDto billDto = new BillDto();
        billDto.setIssuedate(orderUpdate.getDate());
        billDto.setTotalprice(orderUpdate.getTotalprice());
        BillDto responseBill = this.billService.createBill(billDto);
        Bill bill = this.modelMapper.map(responseBill, Bill.class);
        orderUpdate.setBill(bill);

        Order orderSucceed = this.orderRepository.save(orderUpdate);
        return this.modelMapper.map(orderSucceed, ResOrderDto.class);
    }

    @Override
    public ResOrderDto createOrderByCustomer(OrderDto orderDto, Integer checkoutId, Integer deliveryInfoId) {
        Checkout checkout = this.checkOutRepository.findById(checkoutId).orElseThrow(()-> new ResourceNotFoundException("CheckOut","CheckOutId", checkoutId));
        DeliveryInformation deliveryInfo = this.deliveryInfoRepository.findById(deliveryInfoId).orElseThrow(()-> new ResourceNotFoundException("DeliveryInfo","DeliveryInfoId", deliveryInfoId));
        OrderStatus orStatus = this.orderStatusRepository.findOrderStatusByStatus("Shipping");
        Order order = this.modelMapper.map(orderDto, Order.class);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = now.format(formatter);
        order.setDate(formattedDate);

        order.setCheckout(checkout);
        order.setOrderStatus(orStatus);
        order.setDeliveryInformation(deliveryInfo);
        Order addOrder = this.orderRepository.save(order);

        List<ResItemDto> items = this.cartServiceClient.getItemByAccountID(orderDto.getAccountId());
        Double total_price = 0.0;
        for (ResItemDto itemDto : items) {
            ItemOrderedDto ordered = modelMapper.map(itemDto, ItemOrderedDto.class);
            this.itemOrderedService.createItem(ordered, addOrder.getId());
            total_price += itemDto.getPrice()*itemDto.getQuantity();
            this.cartServiceClient.deleteItem(itemDto.getId());
        }
        Integer orderId = addOrder.getId();
        Order orderUpdate = this.orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order","OrderId", orderId));
        orderUpdate.setTotalprice(total_price);

        BillDto billDto = new BillDto();
        billDto.setIssuedate(orderUpdate.getDate());
        billDto.setTotalprice(orderUpdate.getTotalprice());
        BillDto responseBill = this.billService.createBill(billDto);
        Bill bill = this.modelMapper.map(responseBill, Bill.class);
        orderUpdate.setBill(bill);

        Order orderSucceed = this.orderRepository.save(orderUpdate);
        return this.modelMapper.map(orderSucceed, ResOrderDto.class);
    }

    @Override
    public ResOrderDto getOrder(Integer orderId) {
        Order order = this.orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order","OrderId", orderId));
        return this.modelMapper.map(order, ResOrderDto.class);
    }

    @Override
    public void deleteOrder(Integer orderId) {
        Order order = this.orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order","OrderId", orderId));
        this.orderRepository.delete(order);
    }

    @Override
    public ResOrderDto changeStatusOfOrder(Integer orderId, Integer orderStatusId) {
        Order order = this.orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order","OrderId", orderId));
        OrderStatus orderStatus = this.orderStatusRepository.findById(orderStatusId).orElseThrow(()-> new ResourceNotFoundException("OrderStatus","OrderStatusId", orderStatusId));
        order.setOrderStatus(orderStatus);
        Order updateOrder = this.orderRepository.save(order);
        return this.modelMapper.map(updateOrder, ResOrderDto.class);
    }

    @Override
    public List<ResOrderDto> getOrdersByOrderStatus(Integer orderStatusId) {
        OrderStatus order_status = this.orderStatusRepository.findById(orderStatusId).orElseThrow(()-> new ResourceNotFoundException("OrderStatus","OrderStatusId", orderStatusId));
        List<Order> orders = this.orderRepository.findByOrderStatus(order_status);
        List<ResOrderDto> orderDtos = orders.stream().map((order) -> this.modelMapper.map(order, ResOrderDto.class))
                .collect(Collectors.toList());
        return orderDtos;
    }
    @Override
    public List<ResOrderDto> getAllOrder() {
        List<Order> orders = this.orderRepository.findAll();
        List<ResOrderDto> orderDtos = orders.stream().map((order) -> this.modelMapper.map(order, ResOrderDto.class))
                .collect(Collectors.toList());
        return orderDtos;
    }

}
