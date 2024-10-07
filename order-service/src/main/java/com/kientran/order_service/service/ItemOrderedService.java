package com.kientran.order_service.service;

import com.kientran.order_service.dto.ItemOrderedDto;

import java.util.List;


public interface ItemOrderedService {

    ItemOrderedDto createItem(ItemOrderedDto itemOrderedDto, Integer orderId);

}
