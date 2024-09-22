package com.kientran.cart_service.service.impl;

import com.kientran.cart_service.dto.CartDto;
import com.kientran.cart_service.dto.ResCartDto;
import com.kientran.cart_service.entity.Cart;
import com.kientran.cart_service.exception.ResourceNotFoundException;
import com.kientran.cart_service.repository.CartRepository;
import com.kientran.cart_service.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CartDto createCart(CartDto cartDto) {
        Cart cart = this.modelMapper.map(cartDto, Cart.class);
        Cart addCart = this.cartRepository.save(cart);
        return this.modelMapper.map(addCart, CartDto.class);
    }

    @Override
    public CartDto getCartByID(Integer cartId) {
        Cart cart = this.cartRepository.findById(cartId).orElseThrow(()-> new ResourceNotFoundException("Cart","CartId", cartId));
        return this.modelMapper.map(cart, CartDto.class);
    }

    @Override
    public ResCartDto getCartByAccountID(Integer accountId) {
        Cart cart = this.cartRepository.findByAccountId(accountId).orElseThrow(()-> new ResourceNotFoundException("Cart","AccountID", accountId));
        return this.modelMapper.map(cart, ResCartDto.class);
    }

    @Override
    public CartDto findCartByAccountID(Integer accountId) {
        Cart cart = this.cartRepository.findByAccountId(accountId).orElseThrow(()-> new ResourceNotFoundException("Cart","AccountID", accountId));
        return this.modelMapper.map(cart, CartDto.class);
    }

    @Override
    public void deleteCart(Integer cartId) {
        Cart cart = this.cartRepository.findById(cartId).orElseThrow(()-> new ResourceNotFoundException("Cart","CartId", cartId));
        this.cartRepository.delete(cart);
    }

    @Override
    public List<CartDto> getCarts() {
        List<Cart> carts = this.cartRepository.findAll();
        List<CartDto> cartDtos = carts.stream().map((cart) -> this.modelMapper.map(cart, CartDto.class))
                .collect(Collectors.toList());
        return cartDtos;
    }

    @Override
    public void deleteCartByAccountID(Integer accountId) {
        Cart cart = this.cartRepository.findByAccountId(accountId).orElseThrow(()-> new ResourceNotFoundException("Cart","AccountID", accountId));
        this.cartRepository.delete(cart);
    }
}
