package com.kientran.order_service.service.impl;

import com.kientran.order_service.dto.CheckoutDto;
import com.kientran.order_service.entity.Checkout;
import com.kientran.order_service.exception.ResourceNotFoundException;
import com.kientran.order_service.repository.CheckoutRepository;
import com.kientran.order_service.service.CheckoutService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    @Autowired
    private CheckoutRepository checkoutRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CheckoutDto createCheckOut(CheckoutDto checkOutDto) {
        Checkout checkOut = this.modelMapper.map(checkOutDto, Checkout.class);
        Checkout addCheckOut = this.checkoutRepository.save(checkOut);
        return this.modelMapper.map(addCheckOut, CheckoutDto.class);
    }

    @Override
    public void deleteCheckOut(Integer checkOutId) {
        Checkout checkOut = this.checkoutRepository.findById(checkOutId)
                .orElseThrow(()-> new ResourceNotFoundException("CheckOut ","CheckOutId", checkOutId));
        this.checkoutRepository.delete(checkOut);
    }

    @Override
    public List<CheckoutDto> getCheckOuts() {
        List<Checkout> checkOuts = this.checkoutRepository.findAll();
        List<CheckoutDto> checkOutDtos = checkOuts.stream().map((checkOut)-> this.modelMapper.map(checkOut, CheckoutDto.class)).collect(Collectors.toList());
        return checkOutDtos;
    }
}
