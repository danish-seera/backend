package com.kabirclub.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "product_tags")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProductTag {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnoreProperties({"images", "variants", "tags"})
    private Product product;

    @Column(nullable = false)
    private String tag;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
} 