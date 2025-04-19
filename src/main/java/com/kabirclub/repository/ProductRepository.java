package com.kabirclub.repository;

import com.kabirclub.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, String> {
    
    @Query("SELECT p FROM Product p WHERE " +
           "(:query IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%'))) AND " +
           "(:category IS NULL OR p.category = :category)")
    Page<Product> searchProducts(
            @Param("query") String query,
            @Param("category") String category,
            Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.category = :category AND p.id != :id")
    Page<Product> findByCategoryAndIdNot(@Param("category") String category, @Param("id") String id, Pageable pageable);
} 