package com.ecommerce.spring_ecommerce.repository;

import com.ecommerce.spring_ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
