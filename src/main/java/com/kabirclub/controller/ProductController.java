package com.kabirclub.controller;

import com.kabirclub.entity.Product;
import com.kabirclub.scheduler.BottomwearScheduler;
import com.kabirclub.scheduler.ProductScheduler;
import com.kabirclub.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    
    private final ProductService productService;
    private final ProductScheduler productScheduler;
    private final BottomwearScheduler bottomwearScheduler;
    @PostMapping("/add-topwear")
    public ResponseEntity<String> addTopwear() {
        log.info("addTopwear called");
        productScheduler.addNewShirt();
        return ResponseEntity.ok("Success");
    }

    @PostMapping("/add-bottomwear")
    public ResponseEntity<String> addBottomwear() {
        log.info("addBottomwear called");
        bottomwearScheduler.addNewBottomwear();
        return ResponseEntity.ok("Success");
    }
    
    @GetMapping
    public ResponseEntity<Map<String, Object>> getProducts(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortOrder,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) String category) {
        log.info("getProducts called with query: {}, sortBy: {}, sortOrder: {}, page: {}, limit: {}, category: {}", query, sortBy, sortOrder, page, limit, category);
        
        Page<Product> products = productService.getProducts(
                query, sortBy, sortOrder, page, limit, category);
        
        Map<String, Object> response = new HashMap<>();
        response.put("products", products.getContent());
        response.put("total", products.getTotalElements());
        response.put("page", products.getNumber());
        response.put("limit", products.getSize());
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        log.info("getProductById called with id: {}", id);
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
} 