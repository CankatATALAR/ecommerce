package com.ecommerce.spring_ecommerce.repository;

import com.ecommerce.spring_ecommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
}
