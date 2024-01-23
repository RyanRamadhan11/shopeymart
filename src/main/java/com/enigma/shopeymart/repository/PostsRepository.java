package com.enigma.shopeymart.repository;

import com.enigma.shopeymart.entity.Customer;
import com.enigma.shopeymart.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface PostsRepository extends JpaRepository<Posts, Integer> {
}
