package com.kabirclub.model;

import com.kabirclub.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private boolean success;
    private Object data;
    private String message;
    private int statusCode;

    public ProductResponse(boolean success, Product data, String message, int statusCode) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.statusCode = statusCode;
    }

    public ProductResponse(boolean success, com.kabirclub.model.Product data, String message, int statusCode) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.statusCode = statusCode;
    }
} 