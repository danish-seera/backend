package com.kabirclub.controller;

import com.kabirclub.dto.BestSellersResponse;
import com.kabirclub.dto.ProductRecomResponse;
import com.kabirclub.entity.Product;
import com.kabirclub.model.ProductResponse;
import com.kabirclub.scheduler.BottomwearScheduler;
import com.kabirclub.scheduler.ProductScheduler;
import com.kabirclub.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<ProductResponse> getProductById(@PathVariable String id) {
        try {
            log.info("getProductById called with id: {}", id);
            com.kabirclub.model.Product product = productService.getProductByHandle(id);
            if (product == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ProductResponse(false, null, "Product not found", 404));
            }
            
            return ResponseEntity.ok()
                .body(new ProductResponse(true, product, "Product retrieved successfully", 200));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ProductResponse(false, null, "Internal server error", 500));
        }
    }

    @GetMapping("/{id}/recommendations")
    public ResponseEntity<ProductRecomResponse> getProductRecommendations(@PathVariable String id) {
        try {
            log.info("getProductRecommendations called with id: {}", id);
            List<com.kabirclub.model.Product> recommendations = productService.getProductRecommendations(id);
            
            return ResponseEntity.ok()
                .body(new ProductRecomResponse(true, recommendations, "Product recommendations retrieved successfully", 200));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ProductRecomResponse(false, null, "Internal server error", 500));
        }
    }

    @GetMapping("/best-sellers")
    public ResponseEntity<BestSellersResponse> getBestSellers(
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "5") int limit) {
        try {
            log.info("getBestSellers called with category: {}, limit: {}", category, limit);
            List<BestSellersResponse.CategoryProducts> bestSellers = productService.getBestSellers(category, limit);
            
            return ResponseEntity.ok()
                .body(new BestSellersResponse(true, bestSellers, "Best selling products retrieved successfully", 200));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new BestSellersResponse(false, null, "Internal server error", 500));
        }
    }
} 