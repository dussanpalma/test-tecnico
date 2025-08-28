package com.farmatodo.service.impl;

import com.farmatodo.entity.Product;
import com.farmatodo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.farmatodo.service.ProductService;

import java.util.List;

@Service
public class ProductServiceImpl implements  ProductService {

    private final ProductRepository productRepository;

    @Value("${app.min-stock:1}")
    private int minStock;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> searchProducts(String query) {
        return productRepository.findByNameContainingAndStockGreaterThan(query, minStock);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

}