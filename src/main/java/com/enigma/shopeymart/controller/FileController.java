package com.enigma.shopeymart.controller;

import com.enigma.shopeymart.entity.FileStorage;
import com.enigma.shopeymart.service.impl.FileStorageService;
import lombok.RequiredArgsConstructor;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
public class FileController {

    private final FileStorageService fileStorageService;

//    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public String uploadFile(@RequestPart("file") MultipartFile file) {
//        String response = fileStorageService.storageFile(file);
//        return "Success Upload File " + response;
//    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileStorage> uploadFile(@RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(fileStorageService.storageFile(file));
    }


    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        Resource resource;
        try {
            resource = fileStorageService.downloadFile(fileName);
        }catch (FileNotFoundException e){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + resource.getFilename() + "\"")
                .body(resource);

    }

}
