package com.enigma.shopeymart.dto.request;

import com.enigma.shopeymart.entity.OrderDetail;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class OrderDetailRequest {
    private String productPriceId;
    private Integer quantity;
}
