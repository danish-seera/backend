package com.kabirclub.service;

import com.kabirclub.entity.Product;
import com.kabirclub.entity.ProductImage;
import com.kabirclub.repository.ProductImageRepository;
import com.kabirclub.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    
    public Page<Product> getProducts(
            String query,
            String sortBy,
            String sortOrder,
            Integer page,
            Integer limit,
            String category) {
        
        // Default values
        page = page != null ? page : 0;
        limit = limit != null ? limit : 10;
        sortBy = sortBy != null ? sortBy : "createdAt";
        sortOrder = sortOrder != null ? sortOrder : "desc";
        
        // Create sort object
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortBy);
        
        // Create pageable object
        PageRequest pageable = PageRequest.of(page, limit, sort);
        
        // Search products
        Page<Product> products = productRepository.searchProducts(query, category, pageable);
        
        // Fetch and set images for each product
        products.getContent().forEach(product -> {
            List<ProductImage> images = productImageRepository.findByProductId(product.getId());
            product.setImages(images);
        });
        
        return products;
    }
    
    public Optional<Product> getProductById(String id) {
        Optional<Product> product = productRepository.findById(id);
        product.ifPresent(p -> {
            List<ProductImage> images = productImageRepository.findByProductId(p.getId());
            p.setImages(images);
        });
        return product;
    }
} 