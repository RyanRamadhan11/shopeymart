package com.enigma.shopeymart.controller;

import com.enigma.shopeymart.dto.request.StoreRequest;
import com.enigma.shopeymart.dto.response.StoreResponse;
import com.enigma.shopeymart.service.StoreService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@SpringBootTest
class StoreControllerTest {
    // Mocking service dependency
    private StoreService storeService = mock(StoreService.class);

    // Injecting the mocked service into the controller
    private StoreController storeController = new StoreController(storeService);

    @Test
    public void itshouldGetAllDataStoreWhenCallGetAllStores() {
        List<StoreResponse> dummyStores = new ArrayList<>();
        dummyStores.add(new StoreResponse("1","121","Berkah","ragunan","621"));
        dummyStores.add(new StoreResponse("2","122","Berkah 2","ragunan 2","622"));

        when(storeService.getAll()).thenReturn(dummyStores);

        List<StoreResponse> retriveStoreResponse = storeController.getAllStores();

        verify(storeService, times(1)).getAll();

        assertNotNull(retriveStoreResponse);
        assertEquals(dummyStores.get(0).getId(), retriveStoreResponse.get(0).getId());
        assertEquals(dummyStores.get(0).getStoreName(), retriveStoreResponse.get(0).getStoreName());

    }


    @Test
    public void itShouldReturnStoreResponseWhenCreateStore() {
        StoreRequest dummyStoreRequest = new StoreRequest();
        dummyStoreRequest.setId("123");
        dummyStoreRequest.setName("Jaya Selalu");


        StoreResponse dummyStoreResponse = storeController.create(dummyStoreRequest);

        verify(storeService, times(1)).create(dummyStoreRequest);

        assertNotNull(dummyStoreResponse);
        assertEquals(dummyStoreRequest.getId(), dummyStoreResponse.getId());
        assertEquals(dummyStoreRequest.getName(), dummyStoreResponse.getStoreName());
    }

    @Test
    public void itShouldUpdateStoreWhenIdExists() {
        StoreRequest dummyStoreRequest = new StoreRequest("1", "ganti", "123", "ragunan", "123456789");

        StoreResponse storeResponse = new StoreResponse("1", "awal", "123", "ragunan", "123456789");

        when(storeService.update(dummyStoreRequest)).thenReturn(storeResponse);

        StoreResponse dummyStoreResponses = storeController.updateStore(dummyStoreRequest);

        verify(storeService, times(1)).update(dummyStoreRequest);

        assertNotNull(dummyStoreResponses);
        assertEquals(dummyStoreRequest.getId(), dummyStoreResponses.getId());
        assertEquals(dummyStoreRequest.getName(), dummyStoreResponses.getStoreName());
        assertEquals(dummyStoreRequest.getNoSiup(), dummyStoreResponses.getNoSiup());
    }




}