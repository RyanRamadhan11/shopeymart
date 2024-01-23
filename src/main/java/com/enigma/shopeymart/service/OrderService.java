package com.enigma.shopeymart.service;

import com.enigma.shopeymart.dto.request.OrderRequest;
import com.enigma.shopeymart.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse createNewOrder(OrderRequest orderRequest);
    OrderResponse getOrderById(String id);
    List<OrderResponse> getAllOrder();
}
