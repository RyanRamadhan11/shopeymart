package com.enigma.shopeymart.service.impl;

import com.enigma.shopeymart.entity.Customer;
import com.enigma.shopeymart.entity.Posts;
import com.enigma.shopeymart.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final RestTemplate restTemplate;
    private final PostsRepository postsRepository;

    @Value("${api.endpoint.url.post}")
    private String BASE_URL;

    //ini untuk get all posts default
    public ResponseEntity<String> getAllPostsDefault(){
        return responseMethod(restTemplate.getForEntity(BASE_URL, String.class), "Failed to load data");
    }


    //ini untuk get all posts combine
    public ResponseEntity<List<Posts>> getAllPostsCombine(){

        ResponseEntity<Posts[]> openApi = restTemplate.getForEntity(BASE_URL, Posts[].class);
        List<Posts> external = List.of(openApi.getBody());

        List<Posts> localPosts = postsRepository.findAll();

        localPosts.addAll(external);
        return ResponseEntity.ok(localPosts);

//        return responseMethod(List<localPosts>, "Failed to load data");
    }


    public ResponseEntity<String> getPostsById(Long id){
//        String apiUrl = "https://jsonplaceholder.typicode.com/posts/" + id;
//        return responseMethod(restTemplate.getForEntity(apiUrl,String.class), "Failed to load data");

        return responseMethod(restTemplate.getForEntity(BASE_URL + "/" + id, String.class), "Failed to load data");
    }


    public ResponseEntity<String> createPosts(Posts posts){

        //mengatur header permintaan
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        //membungkus data permintaan dalam httpentittyty
        HttpEntity<Posts> requestEntity = new HttpEntity<>(posts, httpHeaders);

        postsRepository.save(posts);

        //response
        return responseMethod(restTemplate.postForEntity(BASE_URL, requestEntity, String.class), "Failed to create data");
    }

    private ResponseEntity<String> responseMethod(ResponseEntity<String> restTemplate, String message){
        ResponseEntity<String> responseEntity = restTemplate;
        if (responseEntity.getStatusCode().is2xxSuccessful()){
            String responseBody = responseEntity.getBody();
            return responseEntity.ok(responseBody);
        }
        return responseEntity.status(responseEntity.getStatusCode()).body(message);

    }

}
