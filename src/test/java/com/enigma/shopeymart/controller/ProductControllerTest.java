package com.enigma.shopeymart.controller;

import com.enigma.shopeymart.dto.request.ProductRequest;
import com.enigma.shopeymart.dto.response.CommonResponse;
import com.enigma.shopeymart.dto.response.CustomerResponse;
import com.enigma.shopeymart.dto.response.PagingResponse;
import com.enigma.shopeymart.dto.response.ProductResponse;
import com.enigma.shopeymart.entity.Product;
import com.enigma.shopeymart.service.CustomerService;
import com.enigma.shopeymart.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createProduct() {
        // Mock request data
        ProductRequest dummyproductRequest = new ProductRequest();
        dummyproductRequest.setProductName("oreo");
        dummyproductRequest.setPrice(100L);

        // Mock response data
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductId("1");
        productResponse.setProductName("oreo");
        productResponse.setPrice(100L);

        Mockito.when(productService.createProductAndProductPrice(dummyproductRequest)).thenReturn(productResponse);

        ResponseEntity<?> responseEntity = productController.createProduct(dummyproductRequest);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        CommonResponse<ProductResponse> commonResponse = (CommonResponse<ProductResponse>) responseEntity.getBody();

        assertNotNull(commonResponse);
        assertEquals(HttpStatus.CREATED.value(), commonResponse.getStatusCode());
        assertEquals("Successfully created new product", commonResponse.getMessage());

        //verify
        Mockito.verify(productService, Mockito.times(1)).createProductAndProductPrice(dummyproductRequest);
    }


    @Test
    void getAllProduct() {
        List<Product> expectedProduct = new ArrayList<>();

        Mockito.when(productService.getAll()).thenReturn(expectedProduct);

        List<Product> actualResponse = productController.getAllProduct();
        assertEquals(expectedProduct, actualResponse);
    }


    @Test
    void getAllProductPage() {
        // Mock request data
        String name = "Product";
        Long maxPrice = 100L;
        Integer page = 0;
        Integer size = 5;

        // Mock response data
        List<ProductResponse> productList = Arrays.asList();

        // Membuat objek PageImpl untuk simulasi halaman hasil
        Page<ProductResponse> productResponses = new PageImpl<>(productList);

        PagingResponse pagingResponse = PagingResponse.builder()
                .currentPage(page)
                .totalPage(productResponses.getTotalPages())
                .size(size)
                .build();

        // Stubbing the service method to return mock data
        Mockito.when(productService.getAllByNameOrPrice(name, maxPrice, page, size)).thenReturn(productResponses);

        ResponseEntity<?> responseEntity = productController.getAllProductPage(name, maxPrice, page, size);

        Mockito.verify(productService, Mockito.times(1)).getAllByNameOrPrice(name, maxPrice, page, size);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        CommonResponse<?> commonResponse = (CommonResponse<?>) responseEntity.getBody();
        assertNotNull(commonResponse);

        assertEquals(HttpStatus.CREATED.value(), commonResponse.getStatusCode());
        assertEquals("Successfully get all product", commonResponse.getMessage());
        assertEquals(productResponses.getContent(), commonResponse.getData());
        assertEquals(pagingResponse, commonResponse.getPaging());
    }
}