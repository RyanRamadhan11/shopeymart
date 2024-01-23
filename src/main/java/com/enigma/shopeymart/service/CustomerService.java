package com.enigma.shopeymart.service;

import com.enigma.shopeymart.dto.request.CustomerRequest;
import com.enigma.shopeymart.dto.response.CustomerResponse;
import com.enigma.shopeymart.entity.Customer;

import java.util.List;

public interface CustomerService {

    List<CustomerResponse> getAll();

    CustomerResponse create(CustomerRequest customerRequest);

    CustomerResponse createNewCustomer(Customer request);

    CustomerResponse update(CustomerRequest customerRequest);

    void delete(String id);

    CustomerResponse getById(String id);

}
