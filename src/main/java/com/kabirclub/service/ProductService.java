package com.kabirclub.service;

import com.kabirclub.entity.ProductImage;
import com.kabirclub.entity.ProductVariant;
import com.kabirclub.dto.BestSellersResponse;
import com.kabirclub.entity.Product;
import com.kabirclub.dto.CreateProductRequest;
import com.kabirclub.repository.ProductImageRepository;
import com.kabirclub.repository.ProductRepository;
import com.kabirclub.repository.ProductVariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductVariantRepository productVariantRepository;

    public Page<Product> getProducts(
            String query,
            String sortBy,
            String sortOrder,
            Integer page,
            Integer limit,
            String category) {

        // Default values
        page = page != null ? page : 0;
        limit = limit != null ? limit : 10;
        // sortBy = "createdAt";
        sortOrder = sortOrder != null ? sortOrder : "desc";

        // Create sort object
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortBy);

        // Create pageable object
        PageRequest pageable = PageRequest.of(page, limit, sort);

        // Search products
        Page<Product> products = productRepository.searchProducts(query, category, pageable);

        // Fetch and set images for each product
        products.getContent().forEach(product -> {

            product.getVariants().stream().forEach(variant -> {
                List<ProductImage> images = productImageRepository.findByProductVariantId(variant.getId());
                variant.setImages(images);
            });
        });

        return products;
    }

    public com.kabirclub.model.Product getProductByHandle(String handle) {
        Optional<Product> product = productRepository.findById(handle);
        if (product.isPresent()) {
            return com.kabirclub.model.Product.builder()
                    .id(product.get().getId())
                    .handle(product.get().getId())
                    .title(product.get().getTitle())
                    .description(product.get().getDescription())
                    .price(product.get().getPrice().doubleValue())
                    .images(buildImages(product.get().getVariants()))
                    .variants(buildVariants(product.get().getVariants()))
                    .build();
        } else {
            return null;
        }
    }

    public com.kabirclub.model.Product getCMSProductByHandle(String handle) {
        Optional<Product> product = productRepository.findById(handle);
        if (product.isPresent()) {
            return com.kabirclub.model.Product.builder()
                    .id(product.get().getId())
                    .handle(product.get().getId())
                    .title(product.get().getTitle())
                    .description(product.get().getDescription())
                    .price(product.get().getPrice().doubleValue())
                    .images(buildImages(product.get().getVariants()))
                    .variants(buildVariants(product.get().getVariants()))
                    .build();
        } else {
            return null;
        }
    }

    private List<com.kabirclub.model.ProductVariant> buildVariants(List<ProductVariant> variants) {
        List<com.kabirclub.model.ProductVariant> productVariants = new ArrayList<>();
        for (ProductVariant variant : variants) {
            productVariants.add(com.kabirclub.model.ProductVariant.builder()
                    .id(variant.getId())
                    .title(variant.getName())
                    .price(variant.getPrice().doubleValue())
                    .availableForSale(variant.getStock() > 0)
                    .images(buildImages(variant))
                    .build());
        }
        return productVariants;
    }

    private List<com.kabirclub.model.ProductImage> buildImages(List<ProductVariant> variants) {
        List<com.kabirclub.model.ProductImage> images = new ArrayList<>();
        for (ProductVariant variant : variants) {
            List<ProductImage> variantImages = productImageRepository.findByProductVariantId(variant.getId());
            for (ProductImage image : variantImages) {
                images.add(com.kabirclub.model.ProductImage.builder()
                        .url(image.getImageUrl())
                        .build());
            }
        }
        return images;
    }

    private List<com.kabirclub.model.ProductImage> buildImages(ProductVariant variant) {
        List<com.kabirclub.model.ProductImage> images = new ArrayList<>();
        List<ProductImage> variantImages = productImageRepository.findByProductVariantId(variant.getId());
            for (ProductImage image : variantImages) {
                images.add(com.kabirclub.model.ProductImage.builder()
                        .url(image.getImageUrl())
                        .build());
            }
        return images;
    }

    public List<com.kabirclub.model.Product> getProductRecommendations(String productId) {
        // Get the current product to find its category
        Optional<Product> currentProduct = productRepository.findById(productId);
        if (currentProduct.isEmpty()) {
            return new ArrayList<>();
        }

        // Get similar products from the same category, excluding the current product
        Page<Product> similarProducts = productRepository.findByCategoryAndIdNot(
            currentProduct.get().getCategory(),
            productId,
            PageRequest.of(0, 3) // Get 3 recommendations
        );

        // Convert to model products
        return similarProducts.getContent().stream()
            .map(product -> com.kabirclub.model.Product.builder()
                .id(product.getId())
                .handle(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice().doubleValue())
                .images(buildImages(product.getVariants()))
                .variants(buildVariants(product.getVariants()))
                .build())
            .collect(Collectors.toList());
    }

    public List<BestSellersResponse.CategoryProducts> getBestSellers(String category, int limit) {
        List<BestSellersResponse.CategoryProducts> result = new ArrayList<>();
        
        // If category is specified, get best sellers for that category only
        if (category != null && !category.isEmpty()) {
            Page<Product> products = productRepository.findBestSellersByCategory(
                category,
                PageRequest.of(0, limit)
            );
            
            result.add(new BestSellersResponse.CategoryProducts(
                category,
                products.getContent().stream()
                    .map(product -> com.kabirclub.model.Product.builder()
                        .id(product.getId())
                        .handle(product.getId())
                        .title(product.getTitle())
                        .description(product.getDescription())
                        .price(product.getPrice().doubleValue())
                        .images(buildImages(product.getVariants()))
                        .variants(buildVariants(product.getVariants()))
                        .build())
                    .collect(Collectors.toList())
            ));
        } else {
            // If no category specified, get best sellers for all categories
            List<String> categories = productRepository.findAllCategories();
            for (String cat : categories) {
                Page<Product> products = productRepository.findBestSellersByCategory(
                    cat,
                    PageRequest.of(0, limit)
                );
                
                result.add(new BestSellersResponse.CategoryProducts(
                    cat,
                    products.getContent().stream()
                        .map(product -> com.kabirclub.model.Product.builder()
                            .id(product.getId())
                            .handle(product.getId())
                            .title(product.getTitle())
                            .description(product.getDescription())
                            .price(product.getPrice().doubleValue())
                            .images(buildImages(product.getVariants()))
                            .variants(buildVariants(product.getVariants()))
                            .build())
                        .collect(Collectors.toList())
                ));
            }
        }
        
        return result;
    }

    public Product createProduct(CreateProductRequest request) {
        // Create main product
        Product product = new Product();
        product.setTitle(request.getTitle());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());
        product.setTags(String.join(",", request.getTags()));
        product.setCreatedAt(LocalDateTime.now());
        product = productRepository.saveAndFlush(product);

        // Create product variants
        ProductVariant variant = new ProductVariant();
        variant.setProduct(product);
        variant.setName(request.getTitle() + " Variant");
        variant.setPrice(request.getPrice());
        variant.setStock(100);
        variant.setCreatedAt(LocalDateTime.now());
        variant = productVariantRepository.saveAndFlush(variant);

        // Create product images
        ProductImage image = new ProductImage();
        image.setProductVariantId(variant.getId());
        image.setImageUrl(request.getImageUrl());
        image.setIsPrimary(true);
        image.setCreatedAt(LocalDateTime.now());
        image = productImageRepository.saveAndFlush(image);

        return product;
    }
}