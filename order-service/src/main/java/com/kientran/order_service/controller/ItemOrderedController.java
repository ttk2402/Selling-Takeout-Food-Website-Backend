package com.kientran.order_service.controller;

import com.kientran.order_service.dto.ItemOrderedDto;
import com.kientran.order_service.service.ItemOrderedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/itemOrdered")
public class ItemOrderedController {

    @Autowired
    private ItemOrderedService itemOrderedService;

    @PostMapping("/add/{orderId}")
    public ResponseEntity<ItemOrderedDto> createProductItem(@RequestBody ItemOrderedDto itemOrderedDto,
                                                            @PathVariable Integer orderId) {
        ItemOrderedDto createItem = this.itemOrderedService.createItem(itemOrderedDto, orderId);
        return new ResponseEntity<ItemOrderedDto>(createItem, HttpStatus.CREATED);
    }

}
