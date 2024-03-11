package com.example.Microservices.service;

import com.example.Microservices.dto.ProductRequest;
import com.example.Microservices.model.Product;
import com.example.Microservices.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {
    private ProductRepository productRepository;
    public void createProduct(ProductRequest productRequest){
        Product product = Product.builder().name(productRequest.getName()).price(productRequest.getPrice()).description(productRequest.getDescription()).build();
        productRepository.save(product);
    }
}
