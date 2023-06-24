package com.example.shoppingonline.repository;

import com.example.shoppingonline.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    List<Product> findByProductname(String keyword);
}
