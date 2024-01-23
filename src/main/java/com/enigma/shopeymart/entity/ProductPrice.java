package com.enigma.shopeymart.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "m_product_price")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ProductPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "stock", columnDefinition = "int check(stock>0)")
    private Integer stock;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "price", columnDefinition = "bigint check(price>0)")
    private Long price;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "product_id")
    //Mengatasi masalah perulangan tak terbatas dalam serialisasi JSON saat entittas memiliki hubungan bidirectional.
    @JsonBackReference
    private Product product;


}
