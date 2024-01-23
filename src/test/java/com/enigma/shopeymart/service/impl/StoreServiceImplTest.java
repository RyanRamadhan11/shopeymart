package com.enigma.shopeymart.service.impl;

import com.enigma.shopeymart.dto.request.StoreRequest;
import com.enigma.shopeymart.dto.response.StoreResponse;
import com.enigma.shopeymart.entity.Store;
import com.enigma.shopeymart.repository.StoreRepository;
import com.enigma.shopeymart.service.StoreService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class StoreServiceImplTest {

//    @Mock
    //    private StoreRepository storeRepository;
    //    private StoreServiceImpl storeService;

    private StoreRepository storeRepository = mock(StoreRepository.class);

    private StoreService storeService = new StoreServiceImpl(storeRepository);

    @Test
    void itShouldReturnStoreResponseWhenCreateNewStore() {

        // Arrange
        StoreRequest dummyStoreRequest = new StoreRequest();
        dummyStoreRequest.setId("123");
        dummyStoreRequest.setName("Jaya Selalu");

//        when(storeRepository.save(any(Store.class))).thenReturn(createdStore);

        // Act
        StoreResponse dummyStoreResponse = storeService.create(dummyStoreRequest);

        // Verifikasi bahwa metode save pada repository dipanggil dengan benar
        verify(storeRepository, times(1)).save(any(Store.class));

        // Assert
        // assertEquals(dummyStoreRequest.getId(),dummyStoreResponse.getId());
        assertEquals(dummyStoreRequest.getName(), dummyStoreResponse.getStoreName());

    }

    @Test
    void itShouldGetAllDataStoreWhenCallGetAll(){
        List<Store> dummyStores = new ArrayList<>();
        dummyStores.add(new Store("1","121","Berkah","ragunan","621"));
        dummyStores.add(new Store("2","122","Berkah 2","ragunan 2","622"));
        dummyStores.add(new Store("3","123","Berkah 3","ragunan 3","623"));

        when(storeRepository.findAll()).thenReturn(dummyStores);

        List<StoreResponse> retriveStores = storeService.getAll();

        assertEquals(dummyStores.size(), retriveStores.size());

        for (int i = 0; i < dummyStores.size(); i++){
            Store dummyStore = dummyStores.get(i);
            StoreResponse retriveStore = retriveStores.get(i);

            assertEquals(dummyStore.getName(), retriveStore.getStoreName());
            assertEquals(dummyStore.getAddress(), retriveStore.getAddress());
        }
    }

    @Test
    void itShouldReturnStoreResponseWhenGetByIdStore() {
        // Arrange
        String storeId = "1";
        Store store = new Store(storeId, "123", "Berkah Selalu", "ragunan", "123456789");

        when(storeRepository.findById(storeId)).thenReturn(Optional.of(store));

        // Act
        StoreResponse storeResponse = storeService.getById(storeId);

        // Verifikasi bahwa metode findById pada repository dipanggil dengan benar
        verify(storeRepository, times(1)).findById(storeId);

        // Assert
        assertNotNull(storeResponse);
        assertEquals(storeId, storeResponse.getId());
        assertEquals("Berkah Selalu" , storeResponse.getStoreName());

//        assertEquals(store.getName(), storeResponse.getStoreName());
//        assertEquals(store.getNoSiup(), storeResponse.getNoSiup());
//        assertEquals(store.getAddress(), storeResponse.getAddress());
//        assertEquals(store.getMobilePhone(), storeResponse.getPhone());

    }


    @Test
    void itShouldDeleteStoreByIdWhenIdExists() {
        // Arrange
        String storeId = "1";
        Store existingStore = new Store(storeId, "123", "Berkah Selalu", "ragunan", "123456789");

        when(storeRepository.findById(storeId)).thenReturn(Optional.ofNullable(existingStore));

        storeService.delete(storeId);

        verify(storeRepository, times(1)).findById(storeId);

        verify(storeRepository, times(1)).deleteById(storeId);
    }

    @Test
    void itShouldUpdateStoreWhenIdExists() {

        StoreRequest dummyStoreRequest = new StoreRequest();
            dummyStoreRequest.setId("1");
            dummyStoreRequest.setName("tes");
            dummyStoreRequest.setNoSiup("123");
            dummyStoreRequest.setAddress("dahan");
            dummyStoreRequest.setMobilePhone("621");

        Store existingStore = new Store("1", "awal", "123", "ragunan", "123456789");

        when(storeRepository.findById(dummyStoreRequest.getId())).thenReturn(Optional.of(existingStore));

        StoreResponse dummyStoreResponse = storeService.update(dummyStoreRequest);

        verify(storeRepository, times(1)).findById(dummyStoreRequest.getId());

        verify(storeRepository, times(1)).save(existingStore);

        assertNotNull(dummyStoreResponse);
        assertEquals(dummyStoreRequest.getId(), dummyStoreResponse.getId());
        assertEquals(dummyStoreRequest.getName(), dummyStoreResponse.getStoreName());
        assertEquals(dummyStoreRequest.getNoSiup(), dummyStoreResponse.getNoSiup());
        assertEquals(dummyStoreRequest.getAddress(), dummyStoreResponse.getAddress());
        assertEquals(dummyStoreRequest.getMobilePhone(), dummyStoreResponse.getPhone());
    }

}

