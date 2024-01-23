package com.enigma.shopeymart.service.impl;

import com.enigma.shopeymart.entity.FileStorage;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

@Service
public class FileStorageService {
    private final Path fileStorageLocation = Paths.get("/home/user/Documents/batch14/Java Advance/HandsOn/shopeymart/src/main/java/com/enigma/shopeymart/file");

    public FileStorageService(){
        try {
            Files.createDirectories(this.fileStorageLocation);

        }catch (Exception e){
            throw new RuntimeException("Could Not Create the directory where the upload file to storage");
        }
    }

    //ini untuk upload file ini type balikannya string
//    public String storageFile(MultipartFile file){
//
//        String mimeType = file.getContentType();
//
//        //check file type just image
//        if (mimeType == null || (!mimeType.startsWith(("image/")))){
//            throw new RuntimeException("Invalid Upload , only upload iamge");
//        }
//
//        try {
//            // get original file namenya
//            String fileName = file.getOriginalFilename();
//
//            // Set path untuk menyimpan file
//            Path targetLocation = this.fileStorageLocation.resolve(fileName);
//
//            // Simpan file ke lokasi yang ditentukan
//            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
//
//            return "File successfully stored: " + fileName;
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to store the file.", e);
//        }
//    }


    //ini type balikannya filestorage
    public FileStorage storageFile(MultipartFile file) {
        String mimeType = file.getContentType();
        if (mimeType == null || (!mimeType.startsWith("image/"))) {
            throw new RuntimeException("Invalid upload, image only.");
        }
        try {
            Path targetLocation = this.fileStorageLocation.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return FileStorage.builder()
                    .fileName(file.getOriginalFilename())
                    .dateTime(LocalDateTime.now())
                    .build();
        } catch (IOException e) {
            throw new RuntimeException("Could not store" + file.getOriginalFilename() + ", please check again " + e);
        }
    }


    //ini yang download file
    public Resource downloadFile(String fileName) throws FileNotFoundException {
        try {
            // Mendapatkan path file yang akan didownload
            Path targetLocation = this.fileStorageLocation.resolve(fileName).normalize();

            Resource resource = new UrlResource(targetLocation.toUri());
            if (resource.exists()){
                return resource;
            }else {
                throw new FileNotFoundException("File Not Found " + fileName);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Failed Not Found " + fileName);
        }
    }






    //ini utnk meanmbahkan file yang diunduh ke lokasi penyaimpanan baru di local sendiri
//    public Resource downloadFile(String fileName) throws FileNotFoundException {
//        try {
//            // Mendapatkan path file yang akan didownload
//            Path targetLocation = this.fileStorageLocation.resolve(fileName).normalize();
//
//            Resource resource = new UrlResource(targetLocation.toUri());
//            if (resource.exists()) {
//                // Menyimpan file yang diunduh ke lokasi penyimpanan baru
//                Path downloadFilePath = downloadLocation.resolve(fileName);
//                Files.copy(targetLocation, downloadFilePath, StandardCopyOption.REPLACE_EXISTING);
//
//                return new UrlResource(downloadFilePath.toUri());
//            } else {
//                throw new FileNotFoundException("File Not Found " + fileName);
//            }
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to download the file. " + e.getMessage(), e);
//        }
//    }


//    public ByteArrayResource downloadFile(String fileName) {
//        try {
//            // Mendapatkan path file yang akan didownload
//            Path filePath = this.fileStorageLocation.resolve(fileName);
//
//            // Membaca file ke dalam byte array
//            byte[] data = Files.readAllBytes(filePath);
//
//            // Mendapatkan tipe media dari file
//            String contentType = Files.probeContentType(filePath);
//
//            // Membuat header untuk respons HTTP
//            HttpHeaders headers = new HttpHeaders();
//            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
//            headers.add(HttpHeaders.CONTENT_TYPE, contentType);
//
//            // Mengembalikan ByteArrayResource
//            return new ByteArrayResource(data);
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to download the file. " + e.getMessage(), e);
//        }
//    }


}
