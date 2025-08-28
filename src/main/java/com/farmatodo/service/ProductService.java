package com.farmatodo.service;


import com.farmatodo.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> searchProducts(String query);
    Product getProductById(Long id);
    Product addProduct(Product product);
}






