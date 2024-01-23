package com.enigma.shopeymart.service.impl;

import com.enigma.shopeymart.dto.request.OrderRequest;
import com.enigma.shopeymart.dto.response.*;
import com.enigma.shopeymart.entity.Customer;
import com.enigma.shopeymart.entity.OrderDetail;
import com.enigma.shopeymart.entity.Order;
import com.enigma.shopeymart.entity.ProductPrice;
import com.enigma.shopeymart.repository.OrderRepository;
import com.enigma.shopeymart.service.CustomerService;
import com.enigma.shopeymart.service.OrderService;
import com.enigma.shopeymart.service.ProductPriceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(rollbackOn = Exception.class)
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final ProductPriceService productPriceService;

    @Override
    public OrderResponse createNewOrder(OrderRequest orderRequest) {
        //Todo 1:  validasi customernya dulu
        CustomerResponse customerResponse = customerService.getById(orderRequest.getCustomerId());

        //Todo 2: convert orderDetailRequest to orderDetail
        List<OrderDetail> orderDetails = orderRequest.getOrderDetails().stream().map(orderDetailRequest ->{
            //Todo 3: validasi product price
            ProductPrice productPrice = productPriceService.getById(orderDetailRequest.getProductPriceId());
            return OrderDetail.builder()
                    .productPrice(productPrice)
                    .quantity(orderDetailRequest.getQuantity())
                    .build();
        }).toList();

        //Todo 4: Create New Order
        Order order = Order.builder()
                .customer(Customer.builder()
                        .id(customerResponse.getId())
                        .build())
                .transDate(LocalDateTime.now())
                .orderDetails(orderDetails)
                .build();
        orderRepository.saveAndFlush(order);

        List<OrderDetailResponse> orderDetailResponses = order.getOrderDetails().stream().map(orderDetail -> {
            //Todo 5: set order dari orderDetail setelah membuat order baru
            orderDetail.setOrder(order);
            System.out.println(order);

            //Todo 6: ubah stok dari jumlah pembelian
            ProductPrice currentProductPrice = orderDetail.getProductPrice();
            currentProductPrice.setStock(currentProductPrice.getStock() - orderDetail.getQuantity());
            return OrderDetailResponse.builder()
                    .orderDetailId(orderDetail.getId())
                    .quantity(orderDetail.getQuantity())
                    //Todo 7: convert product ke productResponse(productPrice)
                    .productResponse(ProductResponse.builder()
                            .productId(currentProductPrice.getProduct().getId())
                            .productName(currentProductPrice.getProduct().getName())
                            .productDescription(currentProductPrice.getProduct().getDescription())
                            .stock(currentProductPrice.getStock())
                            .price(currentProductPrice.getPrice())
                            //Todo 8: convert store ke storeResponse(productPrice)
                            .store(StoreResponse.builder()
                                    .id(currentProductPrice.getStore().getId())
                                    .noSiup(currentProductPrice.getStore().getNoSiup())
                                    .storeName(currentProductPrice.getStore().getName())
                                    .address(currentProductPrice.getStore().getAddress())
                                    .phone(currentProductPrice.getStore().getMobilePhone())
                                    .build())
                            .build())
                    .build();
        }).toList();

        //todo: 9. convert customer ke customerResponse
        CustomerResponse dataCustomer = customerResponse.toBuilder()
                .customerName(customerResponse.getCustomerName())
                .phone(customerResponse.getPhone())
                .address(customerResponse.getAddress())
                .email(customerResponse.getEmail())
                .build();

        //todo: 10. return orderResponse
        return OrderResponse.builder()
                .orderId(order.getId())
                .transDate(order.getTransDate())
                .customerResponse(dataCustomer)
                .orderDetails(orderDetailResponses)
                .build();
    }

    @Override
    public OrderResponse getOrderById(String id) {
        return null;
    }

    @Override
    public List<OrderResponse> getAllOrder() {
        return null;
    }
}
