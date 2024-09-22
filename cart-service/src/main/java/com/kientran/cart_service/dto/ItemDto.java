package com.kientran.cart_service.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ItemDto {

    private Integer Id;

    private Integer productId;

    private int quantity;

    private Double price;

    private String productName;

    private Double productPrice;

    private String productImage;

}
