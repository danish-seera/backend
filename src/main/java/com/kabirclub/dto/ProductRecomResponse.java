package com.kabirclub.dto;

import com.kabirclub.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRecomResponse {
    private boolean success;
    private List<Product> data;
    private String message;
    private int statusCode;
} 