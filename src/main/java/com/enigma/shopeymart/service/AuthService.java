package com.enigma.shopeymart.service;

import com.enigma.shopeymart.dto.request.AuthRequest;
import com.enigma.shopeymart.dto.response.LoginResponse;
import com.enigma.shopeymart.dto.response.RegisterResponse;
import com.enigma.shopeymart.entity.AppUser;

public interface AuthService {
    RegisterResponse registerCustomer(AuthRequest request);

    RegisterResponse registerAdmin(AuthRequest request);

    LoginResponse login(AuthRequest authRequest);
}