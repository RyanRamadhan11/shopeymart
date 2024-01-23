package com.enigma.shopeymart.service.impl;

import com.enigma.shopeymart.dto.request.StoreRequest;
import com.enigma.shopeymart.dto.response.StoreResponse;
import com.enigma.shopeymart.entity.Store;
import com.enigma.shopeymart.repository.StoreRepository;
import com.enigma.shopeymart.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;


    @Override
    public List<StoreResponse> getAll() {

        List<Store> stores = storeRepository.findAll();

        // Mengonversi daftar Store menjadi daftar StoreResponse
        List<StoreResponse> storeResponses = stores.stream()
                .map(store -> StoreResponse.builder()
                        .id(store.getId())
                        .storeName(store.getName())
                        .noSiup(store.getNoSiup())
                        .address(store.getAddress())
                        .phone(store.getMobilePhone())
                        .build())
                .collect(Collectors.toList());

        return storeResponses;

    }

    @Override
    public StoreResponse create(StoreRequest storeRequest) {
        Store store = Store.builder()
                .name(storeRequest.getName())
                .noSiup(storeRequest.getNoSiup())
                .address(storeRequest.getAddress())
                .mobilePhone(storeRequest.getMobilePhone())
                .build();
        storeRepository.save(store);
        return StoreResponse.builder()
                .storeName(store.getName())
                .noSiup(store.getNoSiup())
                .phone(storeRequest.getMobilePhone())
                .build();
    }
    @Override
    public StoreResponse update(StoreRequest storeRequest) {
        Store store = storeRepository.findById(storeRequest.getId()).orElse(null);

        if (store != null) {
            store.setName(storeRequest.getName());
            store.setNoSiup(storeRequest.getNoSiup());
            store.setAddress(storeRequest.getAddress());
            store.setMobilePhone(storeRequest.getMobilePhone());

            storeRepository.save(store);

            return StoreResponse.builder()
                    .id(store.getId())
                    .storeName(store.getName())
                    .noSiup(store.getNoSiup())
                    .address(store.getAddress())
                    .phone(store.getMobilePhone())
                    .build();
        }
        return null;
    }

    @Override
    public void delete(String id) {
        Store store = storeRepository.findById(id).orElse(null);
        if (store != null) {
            storeRepository.deleteById(id);
            System.out.println("delete success");
        } else {
            System.out.println("id not found");
        }
    }

    @Override
    public StoreResponse getById(String id) {
        Store store = storeRepository.findById(id).orElse(null);
        if (store != null) {
            return StoreResponse.builder()
                    .id(store.getId())
                    .storeName(store.getName())
                    .noSiup(store.getNoSiup())
                    .address(store.getAddress())
                    .phone(store.getMobilePhone())
                    .build();
        }
        return null;
    }


    //ini ayang menyenbabkan error
//    @Override
//    public StoreResponse getById(String id) {
//
//        // Mengambil data dari repository menggunakan ID
//        Optional<Store> storeOptional = storeRepository.findById(id);
//
//        // Menangani kasus toko tidak ditemukan
//        Store store = storeOptional.orElse(null);
//
//        // Mengonversi objek Store menjadi objek StoreResponse
//        return StoreResponse.builder()
//                .storeName(store.getName())
//                .noSiup(store.getNoSiup())
//                .phone(store.getMobilePhone())
//                .build();
//    }

//    @Override
//    public StoreResponse create(StoreRequest storeRequest) {
//        Store store = Store.builder()
//                .name(storeRequest.getName())
//                .noSiup(storeRequest.getNoSiup())
//                .address(storeRequest.getAddress())
//                .mobilePhone(storeRequest.getMobilePhone())
//                .build();
//        storeRepository.save(store);
//
//        return StoreResponse.builder()
//                .storeName(store.getName())
//                .noSiup(store.getNoSiup())
//                .address(storeRequest.getNoSiup())
//                .phone(storeRequest.getMobilePhone())
//                .build();
//    }

//    @Override
//    public List<StoreResponse> getAll() {
//        List<Store> stores = storeRepository.findAll();
//        return stores.stream()
//                .map(store -> StoreResponse.builder()
//                        .storeName(store.getName())
//                        .noSiup(store.getNoSiup())
//                        .address(store.getAddress())
//                        .phone(store.getMobilePhone())
//                        .build())
//                .collect(Collectors.toList());
//    }

//
//    @Override
//    public StoreResponse update(StoreRequest storeRequest) {
//        Store store = new Store(
//                storeRequest.getId(),
//                storeRequest.getNoSiup(),
//                storeRequest.getName(),
//                storeRequest.getAddress(),
//                storeRequest.getMobilePhone()
//        );
//        StoreResponse currentStoreId = getById(store.getId());
//        if (currentStoreId != null) {
//            Store updateStore = storeRepository.save(store);
//            return StoreResponse.builder()
//                    .storeName(updateStore.getName())
//                    .noSiup(updateStore.getNoSiup())
//                    .phone(updateStore.getMobilePhone())
//                    .build();
//        }else {
//            return null;
//        }
//    }
//
//    @Override
//    public void delete(String id) {
//
//        StoreResponse store = getById(id);
//
//        if (store != null) {
//            // Jika store ditemukan, lakukan penghapusan
//            storeRepository.deleteById(id);
//        }
//    }
//

}
