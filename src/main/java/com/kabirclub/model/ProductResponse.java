package com.kabirclub.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private boolean success;
    private Product data;
    private String message;
    private int statusCode;
} 