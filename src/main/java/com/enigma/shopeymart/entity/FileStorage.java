package com.enigma.shopeymart.entity;

import lombok.*;

import java.time.LocalDateTime;


@Getter
@Builder(toBuilder = true)
public class FileStorage {
    private String fileName;
    private LocalDateTime dateTime;
}
