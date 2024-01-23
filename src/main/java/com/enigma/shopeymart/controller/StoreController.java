package com.enigma.shopeymart.controller;

import com.enigma.shopeymart.constant.AppPath;
import com.enigma.shopeymart.dto.request.StoreRequest;
import com.enigma.shopeymart.dto.response.StoreResponse;
import com.enigma.shopeymart.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AppPath.STORE)
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    @GetMapping
    public List<StoreResponse> getAllStores() {
        return storeService.getAll();
    }

    @PostMapping
    public StoreResponse create(@RequestBody StoreRequest storeRequest) {
        return storeService.create(storeRequest);
    }

    @PutMapping
    public StoreResponse updateStore(@RequestBody StoreRequest storeRequest) {
        return storeService.update(storeRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteStore(@PathVariable String id) {
        storeService.delete(id);
    }

    @GetMapping("/{id}")
    public StoreResponse getById(@PathVariable String id) {
        return storeService.getById(id);
    }

}
