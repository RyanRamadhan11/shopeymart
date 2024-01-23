package com.enigma.shopeymart.service.impl;

import com.enigma.shopeymart.dto.request.CustomerRequest;
import com.enigma.shopeymart.dto.response.CustomerResponse;
import com.enigma.shopeymart.entity.Customer;
import com.enigma.shopeymart.repository.CustomerRepository;
import com.enigma.shopeymart.repository.StoreRepository;
import com.enigma.shopeymart.service.CustomerService;
import com.enigma.shopeymart.service.StoreService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CustomerServiceImplTest {
    private CustomerRepository customerRepository = mock(CustomerRepository.class);

    private CustomerService customerService = new CustomerServiceImpl(customerRepository);


    @Test
    public void testCreateNewCustomer() {
        // Given
        Customer request = new Customer();
        request.setId("1");
        request.setName("tes");

        // Mock the behavior of customerRepository.saveAndFlush()
        when(customerRepository.saveAndFlush(request)).thenReturn(request);

        // When
        CustomerResponse result = customerService.createNewCustomer(request);

        // Then
        assertNotNull(result);
        // Add more assertions based on the expected behavior
        assertEquals(request.getId(), result.getId());
        assertEquals(request.getName(), result.getCustomerName());

        // Verify that customerRepository.saveAndFlush() was called with the correct argument
        verify(customerRepository, times(1)).saveAndFlush(request);
    }

    @Test
    void itShouldUpdateCustomerWhenIdExists() {

        String id = "1";
        CustomerRequest dummycustomerRequest = new CustomerRequest();
            dummycustomerRequest.setId(id);
            dummycustomerRequest.setName("cus name");
            dummycustomerRequest.setAddress("pasar");
            dummycustomerRequest.setMobilePhone("123456789");
            dummycustomerRequest.setEmail("cus@example.com");

        Customer existingCustomer = new Customer();
            existingCustomer.setId(id);
            existingCustomer.setName("Old Name");
            existingCustomer.setAddress("Old Address");
            existingCustomer.setMobilePhone("1234");
            existingCustomer.setEmail("oldemail@example.com");

        Optional<Customer> optionalCustomer = Optional.of(existingCustomer);

        when(customerRepository.findById(dummycustomerRequest.getId())).thenReturn(optionalCustomer);

        CustomerResponse customerResponse = customerService.update(dummycustomerRequest);

        verify(customerRepository).findById(dummycustomerRequest.getId());
        verify(customerRepository).save(any(Customer.class));

        assertEquals(dummycustomerRequest.getId(), customerResponse.getId());
        assertEquals(dummycustomerRequest.getName(), customerResponse.getCustomerName());
        assertEquals(dummycustomerRequest.getAddress(), customerResponse.getAddress());
        assertEquals(dummycustomerRequest.getMobilePhone(), customerResponse.getPhone());
        assertEquals(dummycustomerRequest.getEmail(), customerResponse.getEmail());
    }

}