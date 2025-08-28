package com.farmatodo.controller;

import com.farmatodo.dto.ProductDTO;
import com.farmatodo.dto.ProductSearchDTO;
import com.farmatodo.entity.Product;
import com.farmatodo.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/search")
    public ResponseEntity<List<Product>> search(@Valid @RequestBody ProductSearchDTO dto) {
        List<Product> products = productService.searchProducts(dto.getQuery());
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@Valid @RequestBody ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());

        Product saved = productService.addProduct(product);
        return ResponseEntity.ok(saved);
    }

}
