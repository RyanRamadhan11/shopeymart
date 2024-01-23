package com.enigma.shopeymart.controller;

import com.enigma.shopeymart.constant.AppPath;
import com.enigma.shopeymart.dto.request.CustomerRequest;
import com.enigma.shopeymart.dto.response.CommonResponse;
import com.enigma.shopeymart.dto.response.CustomerResponse;
import com.enigma.shopeymart.dto.response.LoginResponse;
import com.enigma.shopeymart.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.CUSTOMER)
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public List<CustomerResponse> getAllCustomer() {
        return customerService.getAll();
    }

    @PostMapping
    public CustomerResponse createCustomer(@RequestBody CustomerRequest customerRequest) {
        return customerService.create(customerRequest);
    }

    @GetMapping("/{id}")
    public CustomerResponse getCustomerById(@PathVariable String id) {
        return customerService.getById(id);
    }


    @PutMapping
    public CustomerResponse updateCustomer(@RequestBody CustomerRequest customerRequest) {
        return customerService.update(customerRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable String id) {
        customerService.delete(id);
    }
}
