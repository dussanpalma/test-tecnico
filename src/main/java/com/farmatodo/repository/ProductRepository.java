package com.farmatodo.repository;

import com.farmatodo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingAndStockGreaterThan(String name, int stock);
}
