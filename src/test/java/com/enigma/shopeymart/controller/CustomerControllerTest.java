package com.enigma.shopeymart.controller;

import com.enigma.shopeymart.dto.request.CustomerRequest;
import com.enigma.shopeymart.dto.response.CustomerResponse;
import com.enigma.shopeymart.entity.Customer;
import com.enigma.shopeymart.entity.Store;
import com.enigma.shopeymart.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllCustomer() {
        List<CustomerResponse> expectedResponse = new ArrayList<>();

        Mockito.when(customerService.getAll()).thenReturn(expectedResponse);

        List<CustomerResponse> actualResponse = customerController.getAllCustomer();
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void createCustomer() {
        CustomerRequest dummyCustomerRequest = new CustomerRequest();

        CustomerResponse customerResponse = new CustomerResponse();

        Mockito.when(customerService.create(dummyCustomerRequest)).thenReturn(customerResponse);

        CustomerResponse dummyCustomerResponses = customerController.createCustomer(dummyCustomerRequest);

        Mockito.verify(customerService).create(dummyCustomerRequest);

        assertEquals(customerResponse, dummyCustomerResponses);

        assertNotNull(dummyCustomerResponses);
        assertEquals(dummyCustomerRequest.getId(), dummyCustomerResponses.getId());
        assertEquals(dummyCustomerRequest.getName(), dummyCustomerResponses.getCustomerName());

    }

    @Test
    void getCustomerById() {
        String customerId = "1";

        CustomerResponse expectedCustomerResponse = new CustomerResponse();

        Mockito.when(customerService.getById(customerId)).thenReturn(expectedCustomerResponse);

        CustomerResponse actualCustomerRespon = customerController.getCustomerById(customerId);

        Mockito.verify(customerService).getById(customerId);

        assertEquals(expectedCustomerResponse, actualCustomerRespon);

    }

    @Test
    void updateCustomer() {
        CustomerResponse expextedCustomerResponses = new CustomerResponse();
        CustomerRequest customerRequest = new CustomerRequest();

        Mockito.when(customerService.update(customerRequest)).thenReturn(expextedCustomerResponses);

        CustomerResponse actualResponses = customerController.updateCustomer(customerRequest);

        assertEquals(expextedCustomerResponses, actualResponses);
    }

    @Test
    void deleteCustomer() {
        String customerId = "1";
//        Customer existingCustomer = new Customer();
//
//        Mockito.when(customerService.delete(customerId)).thenReturn(existingCustomer);
        customerController.deleteCustomer(customerId);

        Mockito.verify(customerService).delete(customerId);
    }
}