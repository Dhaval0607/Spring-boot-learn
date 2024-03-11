package com.example.productservice.service;

import com.example.productservice.dto.ProductRequest;
import com.example.productservice.model.Product;
import com.example.productservice.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private ProductRepository productRepository;
    public void createProduct(ProductRequest productRequest){
        Product product = Product.builder().name(productRequest.getName()).price(productRequest.getPrice()).description(productRequest.getDescription()).build();
        productRepository.save(product);
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }
}
