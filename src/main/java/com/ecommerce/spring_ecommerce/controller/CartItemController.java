package com.ecommerce.spring_ecommerce.controller;

import com.ecommerce.spring_ecommerce.dto.CartItemDto;
import com.ecommerce.spring_ecommerce.service.CartItemService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cartItem")
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping("/create")
    public ResponseEntity<CartItemDto> createCartItem(@RequestBody CartItemDto cartItemDto){
        CartItemDto cartItem = cartItemService.createCartItem(cartItemDto, cartItemDto.getQuantity());
        return ResponseEntity.ok(cartItem);
    }

    @GetMapping("/getCartItems")
    public ResponseEntity<List<CartItemDto>> getCartItems(){
        List<CartItemDto> cartItemDtos = cartItemService.getCartItems();
        return ResponseEntity.ok(cartItemDtos);
    }

    @GetMapping("/getCartItemById/{id}")
    public ResponseEntity<CartItemDto> getCartItemById(@PathVariable("id") String id){
        CartItemDto cartItemDto = cartItemService.getCartItemById(id);
        return ResponseEntity.ok(cartItemDto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CartItemDto> updateCartItem(@RequestBody CartItemDto cartItemDto,
                                                      @PathVariable("id") String id){

        CartItemDto cartItem = cartItemService.updateCartItem(cartItemDto, id, cartItemDto.getQuantity());
        return ResponseEntity.ok(cartItem);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteCartItem(@PathVariable("id") String id){
        Boolean delete = cartItemService.deleteCartItem(id);
        return ResponseEntity.ok(delete);
    }

}
