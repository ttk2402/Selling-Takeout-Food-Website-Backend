package com.kientran.order_service.controller;

import com.kientran.order_service.dto.BillDto;
import com.kientran.order_service.service.BillService;
import com.kientran.order_service.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/bill")
public class BillController {

    @Autowired
    private BillService billService;

    @PostMapping("/add")
    public ResponseEntity<BillDto> createBill(@RequestBody BillDto billDto) {
        BillDto createBill = this.billService.createBill(billDto);
        return new ResponseEntity<BillDto>(createBill, HttpStatus.CREATED);
    }

    @GetMapping("/{billId}")
    public ResponseEntity<BillDto> getBill(@PathVariable Integer billId) {
        BillDto billDto = this.billService.getBill(billId);
        return new ResponseEntity<BillDto>(billDto, HttpStatus.OK);
    }

    @DeleteMapping("/{billId}")
    public ResponseEntity<ApiResponse> deleteBillById(@PathVariable Integer billId) {
        this.billService.deleteBill(billId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Bill is deleted successfully!", true),
                HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<BillDto>> getAllBill() {
        List<BillDto> billDtos = this.billService.getAllBill();
        return new ResponseEntity<List<BillDto>>(billDtos, HttpStatus.OK);
    }

}