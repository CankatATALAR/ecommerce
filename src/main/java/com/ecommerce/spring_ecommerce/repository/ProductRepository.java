package com.ecommerce.spring_ecommerce.repository;

import com.ecommerce.spring_ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
