package com.ecommerce.spring_ecommerce.service;

import com.ecommerce.spring_ecommerce.dto.CartDto;
import com.ecommerce.spring_ecommerce.dto.UserDto;
import com.ecommerce.spring_ecommerce.model.Cart;
import com.ecommerce.spring_ecommerce.model.CartItem;
import com.ecommerce.spring_ecommerce.model.User;
import com.ecommerce.spring_ecommerce.repository.CartRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public CartService(CartRepository cartRepository,
                       UserService userService,
                       ModelMapper modelMapper) {
        this.cartRepository = cartRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    public CartDto createCart(CartDto cartDto){
        UserDto userDto = userService.getUserById(cartDto.getUserId());
        if(userDto == null){
            throw new RuntimeException("User Not Found with id: " + cartDto.getUserId());
        }
        User user = modelMapper.map(userDto, User.class);
        Cart cart = modelMapper.map(cartDto, Cart.class);
        cart.setUser(user);
        return modelMapper.map(cartRepository.save(cart), CartDto.class);
    }

    public CartDto getCartById(String id){
        /*Optional<Cart> cart = cartRepository.findById(id);
        if(cart.isPresent()){
            return modelMapper.map(cart.get(), CartDto.class);
        } else {
            throw new RuntimeException("Cart Cannot Found");
        }*/
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new RuntimeException("Cart Not Found"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return modelMapper.map(cart, CartDto.class);
    }

    public Cart getCartEntityById(String id){
        return cartRepository.findById(id).orElseThrow(() -> new RuntimeException("Cart not found with id: " + id));
    }

    @Transactional
    public boolean deleteCart(String id){
        Optional<Cart> cart = cartRepository.findById(id);
        if(cart.isPresent()){
            cartRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
