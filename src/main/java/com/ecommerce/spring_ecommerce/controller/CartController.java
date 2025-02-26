package com.ecommerce.spring_ecommerce.controller;

import com.ecommerce.spring_ecommerce.dto.CartDto;
import com.ecommerce.spring_ecommerce.dto.CartItemDto;
import com.ecommerce.spring_ecommerce.model.CartItem;
import com.ecommerce.spring_ecommerce.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/createCart")
    public ResponseEntity<CartDto> createCart(@RequestBody CartDto cartDto){
        CartDto cart = cartService.createCart(cartDto);
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/getCartById/{id}")
    public ResponseEntity<CartDto> getCartById(@PathVariable("id") String id){
        CartDto cartDto = cartService.getCartById(id);
        return ResponseEntity.ok(cartDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteCart(@PathVariable("id") String id){
        Boolean delete = cartService.deleteCart(id);
        return ResponseEntity.ok(delete);
    }
}
