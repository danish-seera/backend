package com.kabirclub.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "product_variants")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnoreProperties({"images", "variants", "tags"})
    private Product product;

    @Transient
    private List<ProductImage> images = new ArrayList<>();

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private String size;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
} 