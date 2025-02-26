package com.ecommerce.spring_ecommerce.dto;

import com.ecommerce.spring_ecommerce.model.Category;
import com.ecommerce.spring_ecommerce.model.Product;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CategoryDto {
    private String id;
    private String name;
    private List<String> productIds;

    public CategoryDto(){}

    public CategoryDto(Category category){
        /*this.id = category.getId();
        this.name = category.getName();
*/
        /*if(category.getProducts() != null){
            this.productIds = category.getProducts().stream().map(Product::getId).collect(Collectors.toList());
        } else {
            this.productIds = new ArrayList<>();
        }*/
        this.productIds = category.getProducts().stream().map(Product::getId).collect(Collectors.toList());
    }
}
