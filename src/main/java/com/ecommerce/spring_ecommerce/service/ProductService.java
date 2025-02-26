package com.ecommerce.spring_ecommerce.service;

import com.ecommerce.spring_ecommerce.dto.ProductDto;
import com.ecommerce.spring_ecommerce.dto.UserDto;
import com.ecommerce.spring_ecommerce.model.Category;
import com.ecommerce.spring_ecommerce.model.Product;
import com.ecommerce.spring_ecommerce.model.User;
import com.ecommerce.spring_ecommerce.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CategoryService categoryService;

    public ProductService(ProductRepository productRepository, ModelMapper modelMapper, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;
    }

    public ProductDto createProduct(ProductDto productDto){
        Product product = modelMapper.map(productDto, Product.class);
        if (productDto.getCategoryIds() != null && !productDto.getCategoryIds().isEmpty()) {
            assignCategoriesToProduct(product, productDto.getCategoryIds());
        }
        return  modelMapper.map(productRepository.save(product), ProductDto.class);
    }

    public Product getProductEntityById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public List<ProductDto> getProducts(){
        List<Product> products = productRepository.findAll();
        List<ProductDto> dtos = products.stream().map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());

        return  dtos;
    }

    public ProductDto getProductById(String id){
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            return modelMapper.map(product.get(), ProductDto.class);
        }
        throw new RuntimeException("Product Cannot Found");
    }

    public ProductDto updateProduct(ProductDto productDto, String id){
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            product.get().setName(productDto.getName());
            product.get().setPrice(productDto.getPrice());
            product.get().setQuantity(productDto.getQuantity());
            if (productDto.getCategoryIds() != null && !productDto.getCategoryIds().isEmpty()) {
                assignCategoriesToProduct(product.get(), productDto.getCategoryIds());
            }
        }
        return modelMapper.map(productRepository.save(product.get()), ProductDto.class);
    }

    public void assignCategoriesToProduct(Product product, List<String> categoryIds) {
        List<Category> categories = categoryService.getCategoriesByIds(categoryIds);
        product.setCategories(categories);
    }

    public List<Product> getProductsById(List<String> productIds){
        return productRepository.findAllById(productIds);
    }

    public boolean deleteProduct(String id){
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
