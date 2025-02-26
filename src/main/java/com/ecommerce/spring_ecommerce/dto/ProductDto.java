package com.ecommerce.spring_ecommerce.dto;

import com.ecommerce.spring_ecommerce.model.Category;
import com.ecommerce.spring_ecommerce.model.Product;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ProductDto {
    private String id;
    private String name;
    private BigDecimal price;
    private Long quantity;
    private List<String> categoryIds;

    public ProductDto(){}

    public ProductDto(Product product){
        /*if(product.getCategories() != null){
            this.categoryIds = product.getCategories().stream().map(Category::getId).collect(Collectors.toList());
        } else {
            this.categoryIds = new ArrayList<>();
        }*/
        this.categoryIds = product.getCategories().stream().map(Category::getId).collect(Collectors.toList());
    }
}
