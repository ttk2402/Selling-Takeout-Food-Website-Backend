package com.kientran.cart_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "ProductService", url = "http://localhost:9091")
public interface ProductServiceClient {

    @GetMapping("/api/product/{productId}")
    ProductDto findCProductByAccountID(@PathVariable Integer productId);

}