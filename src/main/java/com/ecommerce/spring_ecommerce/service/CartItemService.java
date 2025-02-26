package com.ecommerce.spring_ecommerce.service;

import com.ecommerce.spring_ecommerce.dto.CartDto;
import com.ecommerce.spring_ecommerce.dto.CartItemDto;
import com.ecommerce.spring_ecommerce.dto.ProductDto;
import com.ecommerce.spring_ecommerce.model.Cart;
import com.ecommerce.spring_ecommerce.model.CartItem;
import com.ecommerce.spring_ecommerce.model.Product;
import com.ecommerce.spring_ecommerce.repository.CartItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartService cartService;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    public CartItemService(CartItemRepository cartItemRepository,
                           CartService cartService,
                           ProductService productService,
                           ModelMapper modelMapper) {
        this.cartItemRepository = cartItemRepository;
        this.cartService = cartService;
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    public CartItemDto createCartItem(CartItemDto cartItemDto, long quantity){
        Cart cart = cartService.getCartEntityById(cartItemDto.getCartId());
        Product product = productService.getProductEntityById(cartItemDto.getProductId());

        CartItem cartItem = cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(cartItemDto.getProductId()))
                .findFirst().orElse(new CartItem());

        if(cartItem.getId() == null){
            cartItem = modelMapper.map(cartItemDto, CartItem.class);
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        cartItem.setTotalPrice();
        cart.addItem(cartItem);

        return modelMapper.map(cartItemRepository.save(cartItem), CartItemDto.class);
    }

    public List<CartItemDto> getCartItems(){
        List<CartItem> cartItems = cartItemRepository.findAll();
        List<CartItemDto> dtos = cartItems.stream().map(cartItem -> modelMapper.map(cartItem, CartItemDto.class))
                .collect(Collectors.toList());
        return dtos;
    }

    public CartItemDto getCartItemById(String id){
        Optional<CartItem> cartItem = cartItemRepository.findById(id);
        if(cartItem.isPresent()){
            return modelMapper.map(cartItem.get(), CartItemDto.class);
        }
        throw new RuntimeException("CartItem Cannot Found");
    }

    public CartItemDto updateCartItem(CartItemDto cartItemDto, String id, long quantity){
        Optional<CartItem> cartItem = cartItemRepository.findById(id);
        if(cartItem.isPresent()){

            Product product = productService.getProductEntityById(cartItemDto.getProductId());
            Cart cart = cartService.getCartEntityById(cartItemDto.getCartId());

            cartItem.get().setProduct(product);
            cartItem.get().setCart(cart);
            cartItem.get().setQuantity(quantity);
            cartItem.get().setUnitPrice(product.getPrice());
            cartItem.get().setTotalPrice();
        } else {
            throw new RuntimeException("Cart item not found" + id);
        }

        return modelMapper.map(cartItemRepository.save(cartItem.get()), CartItemDto.class);
    }

    public boolean deleteCartItem(String id){
        Optional<CartItem> cartItem = cartItemRepository.findById(id);
        if(cartItem.isPresent()){
            cartItemRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
