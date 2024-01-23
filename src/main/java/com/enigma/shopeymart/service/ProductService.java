package com.enigma.shopeymart.service;


import com.enigma.shopeymart.dto.request.ProductRequest;
import com.enigma.shopeymart.dto.response.ProductResponse;
import com.enigma.shopeymart.entity.Product;
import org.springframework.data.domain.Page;


import java.util.List;

public interface ProductService {

//    List<ProductResponse> getAll();
//
//    ProductResponse create(ProductRequest productRequest);
//
//    ProductResponse update(ProductRequest productRequest);
//
//    void delete(String id);
//
//    ProductResponse getById(String id);

    Product create(Product product);

    List<Product> getAll();

    Product getById(String id);

    Product update(Product product);

    void delete(String id);


    ProductResponse createProductAndProductPrice(ProductRequest productRequest);

    Page<ProductResponse> getAllByNameOrPrice(String name, Long maxPrice, Integer page, Integer size);
}
