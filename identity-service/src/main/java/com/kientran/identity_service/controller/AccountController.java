package com.kientran.identity_service.controller;

import com.kientran.identity_service.dto.*;
import com.kientran.identity_service.response.ApiResponse;
import com.kientran.identity_service.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/add/{roleId}")
    public ResponseEntity<ResAccountDto> addAccount(@RequestBody AccountDto accountDto,
                                                            @PathVariable Integer roleId) {
        ResAccountDto newAccountDto = this.accountService.createAccount(accountDto, roleId);
        return new ResponseEntity<ResAccountDto>(newAccountDto, HttpStatus.CREATED);
    }


    @DeleteMapping("/{accountId}")
    public ResponseEntity<ApiResponse> deleteAccount(@PathVariable Integer accountId) {
        this.accountService.deleteAccount(accountId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Account is deleted successfully!", true),
                HttpStatus.OK);
    }

    @PutMapping("/block/{accountId}")
    public ResponseEntity<ResAccountDto> changeStatusAccountToBlock(@PathVariable Integer accountId) {
        ResAccountDto updatedAc = this.accountService.blockAccount(accountId);
        return new ResponseEntity<ResAccountDto>(updatedAc, HttpStatus.OK);
    }

    @PutMapping("/open/{accountId}")
    public ResponseEntity<ResAccountDto> changeStatusAccountToOpen(@PathVariable Integer accountId) {
        ResAccountDto updatedAc = this.accountService.openAccount(accountId);
        return new ResponseEntity<ResAccountDto>(updatedAc, HttpStatus.OK);
    }

    @GetMapping("/get/{accountId}")
    public ResponseEntity<ResAccountDto> getAccountDetail(@PathVariable Integer accountId) {
        ResAccountDto accountInfo = this.accountService.getAccount(accountId);
        return new ResponseEntity<ResAccountDto>(accountInfo, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ResAccountDto> loginAccount(@RequestBody LoginDto loginDto) {
        ResAccountDto loginAccountDto = this.accountService.login(loginDto);
        return new ResponseEntity<ResAccountDto>(loginAccountDto, HttpStatus.OK);
    }


    @PutMapping("/{accountId}")
    public ResponseEntity<ResAccountDto> updateInfoAccountById(@RequestBody AccountDto accountDto,
                                                               @PathVariable Integer accountId) {
        ResAccountDto updatedAc = this.accountService.updateAccount(accountDto, accountId);
        return new ResponseEntity<ResAccountDto>(updatedAc, HttpStatus.OK);
    }

    @PutMapping("/changepassword/{accountId}")
    public ResponseEntity<ApiResponse> changePasswordForAccount(@RequestBody ChangePasswordDto changePasswordDto,
                                                                @PathVariable Integer accountId) {
        boolean result = this.accountService.changePassword(accountId, changePasswordDto);
        if(result == true) {
            return new ResponseEntity<ApiResponse>(new ApiResponse("Password is changed successfully!", true),
                    HttpStatus.OK);
        }else {
            return new ResponseEntity<ApiResponse>(new ApiResponse("Password is not correct", false),
                    HttpStatus.OK);
        }

    }

    @GetMapping("/role/{roleId}")
    public ResponseEntity<List<AccountDto>> getListAccountByRole(@PathVariable Integer roleId) {
        List<AccountDto> accountDtos = this.accountService.getAccountsByRole(roleId);
        return new ResponseEntity<List<AccountDto>>(accountDtos, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        List<AccountDto> accounts = this.accountService.getAccounts();
        return ResponseEntity.ok(accounts);
    }

}