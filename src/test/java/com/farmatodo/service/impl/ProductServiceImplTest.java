package com.farmatodo.service.impl;

import com.farmatodo.entity.Product;
import com.farmatodo.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    private Product p1;
    private Product p2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ReflectionTestUtils.setField(productService, "minStock", 1);

        p1 = new Product();
        p1.setId(1L);
        p1.setName("Paracetamol");
        p1.setDescription("Dolor y fiebre");
        p1.setPrice(BigDecimal.valueOf(10.0));
        p1.setStock(5);

        p2 = new Product();
        p2.setId(2L);
        p2.setName("Aspirina");
        p2.setDescription("Dolor de cabeza");
        p2.setPrice(BigDecimal.valueOf(15.5));
        p2.setStock(3);
    }

    @Test
    void testSearchProducts() {
        when(productRepository.findByNameContainingAndStockGreaterThan(eq("a"), anyInt()))
                .thenReturn(Arrays.asList(p1, p2));

        List<Product> result = productService.searchProducts("a");
        assertEquals(2, result.size());
        assertTrue(result.contains(p1));
        assertTrue(result.contains(p2));
    }

    @Test
    void testGetProductByIdFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(p1));

        Product result = productService.getProductById(1L);
        assertEquals(p1, result);
    }

    @Test
    void testGetProductByIdNotFound() {
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.getProductById(999L);
        });

        assertEquals("Product not found", exception.getMessage());
    }

    @Test
    void testAddProduct() {
        when(productRepository.save(p1)).thenReturn(p1);

        Product result = productService.addProduct(p1);
        assertEquals(p1, result);
        verify(productRepository, times(1)).save(p1);
    }
}
