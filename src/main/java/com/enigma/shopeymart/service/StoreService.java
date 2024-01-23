package com.enigma.shopeymart.service;


import com.enigma.shopeymart.dto.request.StoreRequest;
import com.enigma.shopeymart.dto.response.StoreResponse;

import java.util.List;

public interface StoreService {

    List<StoreResponse> getAll();

    StoreResponse update(StoreRequest storeRequest);

    void delete(String id);

    StoreResponse getById(String id);

    StoreResponse create(StoreRequest storeRequest);

}




