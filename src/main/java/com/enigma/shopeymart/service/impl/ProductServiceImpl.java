package com.enigma.shopeymart.service.impl;

import com.enigma.shopeymart.dto.request.ProductRequest;
import com.enigma.shopeymart.dto.response.CustomerResponse;
import com.enigma.shopeymart.dto.response.ProductResponse;
import com.enigma.shopeymart.dto.response.StoreResponse;
import com.enigma.shopeymart.entity.Customer;
import com.enigma.shopeymart.entity.Product;
import com.enigma.shopeymart.entity.ProductPrice;
import com.enigma.shopeymart.entity.Store;
import com.enigma.shopeymart.repository.ProductRepository;
import com.enigma.shopeymart.service.ProductPriceService;
import com.enigma.shopeymart.service.ProductService;
import com.enigma.shopeymart.service.StoreService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final StoreService storeService;
    private final ProductPriceService productPriceService;

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product getById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product update(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(String id) {
        productRepository.deleteById(id);
    }


//    @Override
//    public ProductResponse create(ProductRequest productRequest) {
//        Product product = Product.builder()
//                    .name(productRequest.getProductName())
//                    .description(productRequest.getDescription())
//                    .build();
//            productRepository.save(product);
//            return ProductResponse.builder()
//                    .productName(product.getName())
//                    .productDescription(product.getDescription())
//                    .build();
//    }

//    @Override
//    public List<ProductResponse> getAll() {
//        List<Product> products = productRepository.findAll();
//        return products.stream()
//                .map(product -> ProductResponse.builder()
//                        .productId(product.getId())
//                        .productName(product.getName())
//                        .productDescription(product.getDescription())
//                        .build())
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public ProductResponse getById(String id) {
//        Product product = productRepository.findById(id).orElse(null);
//        if (product != null) {
//            return ProductResponse.builder()
//                    .productName(product.getName())
//                    .productDescription(product.getDescription())
//                    .build();
//        }
//        return null;
//    }
//
//    @Override
//    public ProductResponse update(ProductRequest productRequest) {
//        Optional<Product> optionalProduct = productRepository.findById(productRequest.getProductId());
//
//        if (optionalProduct .isPresent()){
//            Product product = Product.builder()
//                    .name(productRequest.getProductName())
//                    .description(productRequest.getDescription())
//                    .build();
//            productRepository.save(product);
//            return ProductResponse.builder()
//                    .productName(product.getName())
//                    .productDescription(product.getDescription())
//                    .build();
//        }else {
//            return null;
//        }
//    }
//
//    @Override
//    public void delete(String id) {
//        Product product = productRepository.findById(id).orElse(null);
//        if (product != null) {
//            productRepository.deleteById(id);
//            System.out.println("delete succed");
//        } else {
//            System.out.println("id not found");
//        }
//    }

    @Override
    public ProductResponse createProductAndProductPrice(ProductRequest productRequest) {
        StoreResponse storeResponse = storeService.getById(productRequest.getStoreId().getId());
//        ini untuk paramater toprdocutrespon yang extrak
//        Store store = new Store();

        Product product = Product.builder()
                .name(productRequest.getProductName())
                .description(productRequest.getDescription())
                .build();
        //saveAndFlush used to add data using synchronous
        productRepository.saveAndFlush(product);

        ProductPrice productPrice = ProductPrice.builder()
                .price(productRequest.getPrice())
                .stock(productRequest.getStock())
                .isActive(true)
                .product(product)
                .store(Store.builder()
                        .id(storeResponse.getId())
                        .build())
                .build();

        productPriceService.create(productPrice);

        return ProductResponse.builder()
                .productId(product.getId())
                .productName(product.getName())
                .productDescription(product.getDescription())
                .price(productPrice.getPrice())
                .stock(productPrice.getStock())
                .store(StoreResponse.builder()
                        .id(storeResponse.getId())
                        .noSiup(storeResponse.getNoSiup())
                        .storeName(storeResponse.getStoreName())
                        .address(storeResponse.getAddress())
                        .phone(storeResponse.getPhone())
                        .build())
                .build();

        //menggunakan extrak method
//        return toProductResponse(store, product, productPrice);
    }

    @Override
    public Page<ProductResponse> getAllByNameOrPrice(String name, Long maxPrice, Integer page, Integer size) {
        //Specificaion untuk menentukan kriteria pencarian, disini criteria pencarian ditandakan dengan root, root yang dimaksud adalah entity product
        Specification<Product> specification = (root, query, criteriaBuilder) -> {

            //join digunakan untuk merelasikan antara product dan product price
            Join<Product, ProductPrice> productPrices = root.join("productPrices");

            //Predicate digunakan untuk menggunakan Like dimana nanti kita akan menggunakan kondisi pencarian paramater
            //Disini kita akan mencari mana product atau harga yang sama atau harga dibwahnya, makannya menggunkan lessThanOrEqualsTo
            List<Predicate> predicates = new ArrayList<>();
            if (name != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(productPrices.get("price"), maxPrice));
            }

            // kode return mengembalikan query dimana pada dasarnya kita membangun klausa where yang sudah ditentukan dari predicate atau criteria
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };

        Pageable pageable = PageRequest.of(page, size);

        Page<Product> products = productRepository.findAll(specification, pageable);

        //digunakan untuk menyimpan respon product yang baru
        List<ProductResponse> productResponses = new ArrayList<>();
        for (Product product : products.getContent()) {
            // for disini digunkan untuk mengiterasi daftar product yang disimpan dalam objek

            //optional untuk mencari harga yang aktif
            Optional<ProductPrice> productPrice = product.getProductPrices()
                    .stream()
                    .filter(ProductPrice::isActive).findFirst();

            //kondisi ini dgunakan untuk mengecek produk price itu kosong atau tidak, kalai tidak maka di skip
            if (productPrice.isEmpty()) continue;

            Store store = productPrice.get().getStore();
            productResponses.add(toProductResponse(store, product, productPrice.get()));

            //sebelum di extrak method
//            productResponses.add(ProductResponse.builder()
//                    .productId(product.getId())
//                    .productName(product.getName())
//                    .productDescription(product.getDescription())
//                    .price(productPrice.get().getPrice())
//                    .stock(productPrice.get().getStock())
//                    .store(StoreResponse.builder()
//                            .id(store.getId())
//                            .storeName(store.getName())
//                            .noSiup(store.getNoSiup())
//                            .address(store.getAddress())
//                            .phone(store.getMobilePhone())
//                            .build())
//                    .build());

        }
        return new PageImpl<>(productResponses, pageable, products.getTotalElements());
    }

    //hasil extrak method builder
    private static ProductResponse toProductResponse(Store store, Product product, ProductPrice productPrice) {
        return ProductResponse.builder()
                .productId(product.getId())
                .productName(product.getName())
                .productDescription(product.getDescription())
                .price(productPrice.getPrice())
                .stock(productPrice.getStock())
                .store(StoreResponse.builder()
                        .id(store.getId())
                        .storeName(store.getName())
                        .noSiup(store.getNoSiup())
                        .address(store.getAddress())
                        .phone(store.getMobilePhone())
                        .build())
                .build();
    }


}
