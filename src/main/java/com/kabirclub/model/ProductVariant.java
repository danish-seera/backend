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
public class ProductVariant {
    private String id;
    private String title;
    private double price;
    private boolean availableForSale;
    private List<ProductOption> selectedOptions;
    private List<ProductImage> images;
}
