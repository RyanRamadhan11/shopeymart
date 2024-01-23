package com.enigma.shopeymart.controller;

import com.enigma.shopeymart.constant.AppPath;
import com.enigma.shopeymart.dto.request.OrderRequest;
import com.enigma.shopeymart.dto.request.ProductRequest;
import com.enigma.shopeymart.dto.response.CommonResponse;
import com.enigma.shopeymart.dto.response.OrderResponse;
import com.enigma.shopeymart.dto.response.PagingResponse;
import com.enigma.shopeymart.dto.response.ProductResponse;
import com.enigma.shopeymart.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.ORDER)
public class OrderController {
   private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createNewOrder(@RequestBody OrderRequest orderRequest){
        OrderResponse orderResponse = orderService.createNewOrder(orderRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.<OrderResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully created new order")
                        .data(orderResponse)
                        .build());
    }
}
