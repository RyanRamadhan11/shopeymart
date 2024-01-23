package com.enigma.shopeymart.service.impl;

import com.enigma.shopeymart.entity.AppUser;
import com.enigma.shopeymart.entity.UserCredential;
import com.enigma.shopeymart.repository.UserCredentialRepository;
import com.enigma.shopeymart.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserCredentialRepository userCredentialRepository;

    @Override
    //method ini untuk memverifikasi jwt nya
    public AppUser loadUserByUserId(String id) {
        UserCredential userCredential = userCredentialRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException("invalid credential"));

        return AppUser.builder()
                .id(userCredential.getId())
                .username(userCredential.getUsername())
                .password(userCredential.getPassword())
                .role(userCredential.getRole().getName())
                .build();
    }

    @Override
    //method ini untuk cek by usernamenya sebagai authentication untuk login
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        UserCredential userCredential = userCredentialRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("invalid credential"));

        return AppUser.builder()
                .id(userCredential.getId())
                .username(userCredential.getUsername())
                .password(userCredential.getPassword())
                .role(userCredential.getRole().getName())
                .build();
    }
}




