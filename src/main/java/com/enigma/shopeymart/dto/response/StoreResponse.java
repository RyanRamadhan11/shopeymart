package com.enigma.shopeymart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class StoreResponse {
    private String id;
    private String noSiup;
    private String storeName;
    private String address;
    private String phone;
}
