package com.ecommerce.spring_ecommerce.service;

import com.ecommerce.spring_ecommerce.dto.CategoryDto;
import com.ecommerce.spring_ecommerce.model.Category;
import com.ecommerce.spring_ecommerce.model.Product;
import com.ecommerce.spring_ecommerce.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ProductService productService;

    //@Lazy annotation is used to prevent circular dependencies.
    public CategoryService(CategoryRepository categoryRepository, ModelMapper modelMapper, @Lazy ProductService productService) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.productService = productService;
    }

    public CategoryDto createCategory(CategoryDto categoryDto){
        Category category = modelMapper.map(categoryDto, Category.class);
        // Eğer productIds varsa, ilişkili ürünleri bağla
        if (categoryDto.getProductIds() != null && !categoryDto.getProductIds().isEmpty()) {
            assignProductsToCategory(category, categoryDto.getProductIds());
        }
        return  modelMapper.map(categoryRepository.save(category), CategoryDto.class);
    }

    public List<CategoryDto> getCategories(){
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> dtos = categories.stream().map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
        return dtos;
    }

    public CategoryDto getCategoryById(String id){
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isPresent()){
            return modelMapper.map(category.get(), CategoryDto.class);
        }
        throw new RuntimeException("Category Cannot Found");
    }

    public CategoryDto updateCategory(CategoryDto categoryDto, String id){
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isPresent()){
            category.get().setName(categoryDto.getName());
            if(categoryDto.getProductIds() != null && !categoryDto.getProductIds().isEmpty()){
                assignProductsToCategory(category.get(), categoryDto.getProductIds());
            }
        }
        return modelMapper.map(categoryRepository.save(category.get()), CategoryDto.class);
    }

    public void assignProductsToCategory(Category category, List<String> productIds) {
        List<Product> products = productService.getProductsById(productIds);
        category.setProducts(products);
    }

    public List<Category> getCategoriesByIds(List<String> categoryIds) {
        return categoryRepository.findAllById(categoryIds);
    }

    public boolean deleteCategory(String id){
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isPresent()){
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
