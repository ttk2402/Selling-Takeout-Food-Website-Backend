package com.kientran.identity_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "CartService", url = "http://localhost:9092")
public interface CartServiceClient {

    @PostMapping("/api/cart/add")
    CartDto createCart(@RequestBody CartDto cartDto);

    @DeleteMapping("/api/cart/account/{accountId}")
    void deleteCartByAccountID(@PathVariable Integer accountId);

}
