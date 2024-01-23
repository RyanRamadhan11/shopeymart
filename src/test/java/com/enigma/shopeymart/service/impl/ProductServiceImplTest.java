package com.enigma.shopeymart.service.impl;

import com.enigma.shopeymart.dto.request.ProductRequest;
import com.enigma.shopeymart.dto.request.StoreRequest;
import com.enigma.shopeymart.dto.response.ProductResponse;
import com.enigma.shopeymart.dto.response.StoreResponse;
import com.enigma.shopeymart.entity.Product;
import com.enigma.shopeymart.entity.ProductPrice;
import com.enigma.shopeymart.entity.Store;
import com.enigma.shopeymart.repository.ProductRepository;
import com.enigma.shopeymart.service.ProductPriceService;
import com.enigma.shopeymart.service.ProductService;
import com.enigma.shopeymart.service.StoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductServiceImplTest {

    private final ProductRepository productRepository = mock(ProductRepository.class);

    private final ProductPriceService productPriceService = mock(ProductPriceService.class);

    private final StoreService storeService = mock(StoreService.class);

    private final ProductService productService = new ProductServiceImpl(productRepository, storeService, productPriceService);


    @BeforeEach
    public void setUp() {
        //reset untuk mock behavior
        reset(productRepository, storeService, productPriceService);
    }

    @Test
    void createProductAndProductPrice() {

        //store request
        StoreResponse dummyStore = new StoreResponse();
        dummyStore.setId("1");
        dummyStore.setStoreName("Berkah Selalu");
        dummyStore.setNoSiup("123");

        when(storeService.getById(anyString())).thenReturn(dummyStore);

        Product saveProduct = new Product();
        saveProduct.setId("productId");
        saveProduct.setName("oleo");
        saveProduct.setDescription("hitam manis");

        when(productRepository.saveAndFlush(any(Product.class))).thenReturn(saveProduct);

        //data dummy product request
        ProductRequest dummyProductRequest = mock(ProductRequest.class);
        when(dummyProductRequest.getStoreId()).thenReturn(StoreResponse.builder()
                .id("storeId")
                .build());
        when(dummyProductRequest.getProductName()).thenReturn(saveProduct.getName());
        when(dummyProductRequest.getDescription()).thenReturn(saveProduct.getDescription());
        when(dummyProductRequest.getPrice()).thenReturn(1000L);
        when(dummyProductRequest.getStock()).thenReturn(10);

        ProductResponse productResponse = productService.createProductAndProductPrice(dummyProductRequest);

        //validate responses
        assertNotNull(productResponse);
        assertEquals(saveProduct.getName() , productResponse.getProductName());

        //validate that the product price was set correct
        assertEquals(dummyProductRequest.getPrice() , productResponse.getPrice());
        assertEquals(dummyProductRequest.getStock() , productResponse.getStock());

        //vaidate interaction with store
        assertEquals(dummyStore.getId(), productResponse.getStore().getId());

        //verify interaction with mock object
        verify(storeService).getById(anyString());
        verify(productRepository).saveAndFlush(any(Product.class));
        verify(productPriceService).create(any(ProductPrice.class));
    }



}