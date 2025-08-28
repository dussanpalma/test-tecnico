package com.farmatodo.controller;

import com.farmatodo.dto.ProductDTO;
import com.farmatodo.dto.ProductSearchDTO;
import com.farmatodo.entity.Product;
import com.farmatodo.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    private ProductService productService;
    private ProductController controller;

    @BeforeEach
    void setUp() {
        productService = mock(ProductService.class);
        controller = new ProductController(productService);
    }

    @Test
    void testSearch() {
        ProductSearchDTO dto = new ProductSearchDTO();
        dto.setQuery("aspirin");

        Product product = new Product();
        product.setId(1L);
        product.setName("Aspirin");

        when(productService.searchProducts("aspirin")).thenReturn(List.of(product));

        ResponseEntity<List<Product>> response = controller.search(dto);

        verify(productService).searchProducts("aspirin");
        assertNotNull(response);
        assertEquals(1, response.getBody().size());
        assertEquals(product, response.getBody().get(0));
    }

    @Test
    void testGetById() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Aspirin");

        when(productService.getProductById(1L)).thenReturn(product);

        ResponseEntity<Product> response = controller.getById(1L);

        verify(productService).getProductById(1L);
        assertNotNull(response);
        assertEquals(product, response.getBody());
    }

    @Test
    void testAddProduct() {
        ProductDTO dto = new ProductDTO();
        dto.setName("Aspirin");
        dto.setDescription("Painkiller");
        dto.setPrice(new BigDecimal("9.99"));
        dto.setStock(100);

        Product saved = new Product();
        saved.setId(1L);
        saved.setName(dto.getName());
        saved.setDescription(dto.getDescription());
        saved.setPrice(dto.getPrice());
        saved.setStock(dto.getStock());

        when(productService.addProduct(any(Product.class))).thenReturn(saved);

        ResponseEntity<Product> response = controller.addProduct(dto);

        verify(productService).addProduct(any(Product.class));
        assertNotNull(response);
        assertEquals(saved, response.getBody());
    }
}
