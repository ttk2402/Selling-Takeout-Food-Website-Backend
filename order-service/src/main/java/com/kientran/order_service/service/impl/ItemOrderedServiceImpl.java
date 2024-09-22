package com.kientran.order_service.service.impl;

import com.kientran.order_service.dto.ItemOrderedDto;
import com.kientran.order_service.entity.ItemOrdered;
import com.kientran.order_service.entity.Order;
import com.kientran.order_service.exception.ResourceNotFoundException;
import com.kientran.order_service.repository.ItemOrderedRepository;
import com.kientran.order_service.repository.OrderRepository;
import com.kientran.order_service.service.ItemOrderedService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemOrderedServiceImpl implements ItemOrderedService {

    @Autowired
    private ItemOrderedRepository itemOrderedRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ItemOrderedDto createItem(ItemOrderedDto itemOrderedDto, Integer orderId) {
        Order order = this.orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order ", "OrderId", orderId));
        ItemOrdered itemOrdered = this.modelMapper.map(itemOrderedDto, ItemOrdered.class);
        itemOrdered.setOrder(order);
        ItemOrdered newItemOrdered = this.itemOrderedRepository.save(itemOrdered);
        return this.modelMapper.map(newItemOrdered, ItemOrderedDto.class);
    }

}
