package com.ecommerce.spring_ecommerce.dto;


import com.ecommerce.spring_ecommerce.model.Cart;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CartDto {
    private String id;
    private String userId;
    private List<CartItemDto> cartItems;

    public CartDto(){}

    public CartDto(Cart cart){
        this.id = cart.getId();
        this.userId = cart.getUser().getId();
        this.cartItems = cart.getCartItems().stream().map(CartItemDto::new).collect(Collectors.toList());
    }
}
