package com.example.shoppingonline.repository;

import com.example.shoppingonline.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {
}
