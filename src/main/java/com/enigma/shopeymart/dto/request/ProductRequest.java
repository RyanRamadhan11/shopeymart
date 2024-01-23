package com.enigma.shopeymart.dto.request;

import com.enigma.shopeymart.dto.response.StoreResponse;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ProductRequest {
    private String productId;

    @NotBlank(message = "product name is required")
    private String productName;

    @NotBlank(message = "description is required")
    private String description;

    @NotBlank(message = "product price is required")
    @Min(value = 0,message = "product price must be greater than 0")
    private Long price;

    @NotBlank(message = "product stock is required")
    @Min(value = 0,message = "stock must be greater than 0")
    private Integer stock;

    @NotBlank(message = "storeId is required")
    private StoreResponse storeId;
}
