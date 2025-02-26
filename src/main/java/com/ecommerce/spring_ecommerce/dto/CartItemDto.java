package com.ecommerce.spring_ecommerce.dto;

import com.ecommerce.spring_ecommerce.model.CartItem;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDto {
    private String id;
    private Long quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private String cartId;
    private String productId;

    public CartItemDto(){}

    public CartItemDto(CartItem cartItem) {
        this.id = cartItem.getId();
        this.quantity = cartItem.getQuantity();
        this.unitPrice = cartItem.getUnitPrice();
        this.totalPrice = cartItem.getTotalPrice();
        this.cartId = cartItem.getCart().getId();
        this.productId = cartItem.getProduct().getId();
    }
}
