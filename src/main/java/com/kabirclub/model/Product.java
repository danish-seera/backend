package com.kabirclub.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    private String id;
    private String handle;
    private String title;
    private String description;
    private double price;
    private List<ProductImage> images;
    private List<ProductVariant> variants;
    private ProductSEO seo;
} 