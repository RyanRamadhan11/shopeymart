package com.enigma.shopeymart.service;

import com.enigma.shopeymart.entity.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
   AppUser loadUserByUserId(String id);

}
