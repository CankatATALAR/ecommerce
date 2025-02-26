package com.ecommerce.spring_ecommerce.repository;

import com.ecommerce.spring_ecommerce.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, String> {
}
