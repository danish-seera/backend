package com.kabirclub.dto;

import com.kabirclub.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BestSellersResponse {
    private boolean success;
    private List<CategoryProducts> data;
    private String message;
    private int statusCode;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryProducts {
        private String category;
        private List<Product> products;
    }
} 