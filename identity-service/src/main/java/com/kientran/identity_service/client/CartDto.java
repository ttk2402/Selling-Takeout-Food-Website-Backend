package com.kientran.identity_service.client;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CartDto {

    private Integer id;

    private Integer accountId;

    public CartDto(Integer accountId) {
        this.accountId = accountId;
    }
}