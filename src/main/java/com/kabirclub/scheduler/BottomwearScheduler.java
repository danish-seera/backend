package com.kabirclub.scheduler;

import com.kabirclub.entity.Product;
import com.kabirclub.entity.ProductImage;
import com.kabirclub.entity.ProductVariant;
import com.kabirclub.repository.ProductImageRepository;
import com.kabirclub.repository.ProductRepository;
import com.kabirclub.repository.ProductVariantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BottomwearScheduler {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductVariantRepository productVariantRepository;

    private final List<String> bottomwearTypes = Arrays.asList(
        "Jeans", "Chinos", "Cargo Pants", "Track Pants", "Shorts", "Joggers"
    );

    private final List<String> bottomwearColors = Arrays.asList(
        "Blue", "Black", "Gray", "Khaki", "Navy", "Olive", "Beige"
    );

    private final List<String> bottomwearBrands = Arrays.asList(
        "Levi's", "Wrangler", "H&M", "Zara", "Nike", "Adidas", "Puma"
    );

    private final List<String> imageUrls = Arrays.asList(
        // Jeans
        "https://images.unsplash.com/photo-1542272604-787c3835535d?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8amVhbnN8ZW58MHx8MHx8fDA%3D",
        "https://images.unsplash.com/photo-1541099649105-f69ad21f3246?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NHx8amVhbnN8ZW58MHx8MHx8fDA%3D",
        "https://images.unsplash.com/photo-1473966968600-fa801b869a1a?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Nnx8amVhbnN8ZW58MHx8MHx8fDA%3D",
        "https://images.unsplash.com/photo-1541099649105-f69ad21f3246?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NHx8amVhbnN8ZW58MHx8MHx8fDA%3D",
        "https://images.unsplash.com/photo-1473966968600-fa801b869a1a?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Nnx8amVhbnN8ZW58MHx8MHx8fDA%3D",
        
        // Chinos
        "https://images.unsplash.com/photo-1624378439575-d8705ad7ae80?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8Y2hpbm98ZW58MHx8MHx8fDA%3D",
        "https://images.unsplash.com/photo-1624378439575-d8705ad7ae80?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8Y2hpbm98ZW58MHx8MHx8fDA%3D",
        "https://images.unsplash.com/photo-1624378439575-d8705ad7ae80?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8Y2hpbm98ZW58MHx8MHx8fDA%3D",
        
        // Cargo Pants
        "https://images.unsplash.com/photo-1624378439575-d8705ad7ae80?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8Y2FyZ28lMjBwYW50c3xlbnwwfHwwfHx8MA%3D%3D",
        "https://images.unsplash.com/photo-1624378439575-d8705ad7ae80?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8Y2FyZ28lMjBwYW50c3xlbnwwfHwwfHx8MA%3D%3D",
        "https://images.unsplash.com/photo-1624378439575-d8705ad7ae80?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8Y2FyZ28lMjBwYW50c3xlbnwwfHwwfHx8MA%3D%3D",
        
        // Track Pants
        "https://images.unsplash.com/photo-1624378439575-d8705ad7ae80?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8dHJhY2slMjBwYW50c3xlbnwwfHwwfHx8MA%3D%3D",
        "https://images.unsplash.com/photo-1624378439575-d8705ad7ae80?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8dHJhY2slMjBwYW50c3xlbnwwfHwwfHx8MA%3D%3D",
        "https://images.unsplash.com/photo-1624378439575-d8705ad7ae80?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8dHJhY2slMjBwYW50c3xlbnwwfHwwfHx8MA%3D%3D",
        
        // Shorts
        "https://images.unsplash.com/photo-1624378439575-d8705ad7ae80?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8c2hvcnRzfGVufDB8fDB8fHww",
        "https://images.unsplash.com/photo-1624378439575-d8705ad7ae80?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8c2hvcnRzfGVufDB8fDB8fHww",
        "https://images.unsplash.com/photo-1624378439575-d8705ad7ae80?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8c2hvcnRzfGVufDB8fDB8fHww",
        
        // Joggers
        "https://images.unsplash.com/photo-1624378439575-d8705ad7ae80?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8am9nZ2Vyc3xlbnwwfHwwfHx8MA%3D%3D",
        "https://images.unsplash.com/photo-1624378439575-d8705ad7ae80?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8am9nZ2Vyc3xlbnwwfHwwfHx8MA%3D%3D",
        "https://images.unsplash.com/photo-1624378439575-d8705ad7ae80?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8am9nZ2Vyc3xlbnwwfHwwfHx8MA%3D%3D"
    );

    @Scheduled(fixedRate = 10*1000)
    public void addNewBottomwear() {
        log.info("Adding new bottomwear");
        // Generate random bottomwear details
        String brand = bottomwearBrands.get((int) (Math.random() * bottomwearBrands.size()));
        String type = bottomwearTypes.get((int) (Math.random() * bottomwearTypes.size()));
        String color = bottomwearColors.get((int) (Math.random() * bottomwearColors.size()));
        Double basePrice = 29 + (Math.random() * 40); // Random price between 29.99 and 69.99

        // Create product
        Product product = new Product();
        product.setTitle(brand + " " + type + " " + color);
        product.setDescription("High quality " + type.toLowerCase() + " in " + color.toLowerCase() + " from " + brand);
        product.setPrice(new BigDecimal(basePrice));
        product.setCategory("bottomwear");
        product.setCreatedAt(LocalDateTime.now());
        product = productRepository.saveAndFlush(product);

        // Add variants
        List<String> sizes = Arrays.asList("28", "30", "32", "34", "36");
        List<String> lengths = Arrays.asList("30", "32", "34");
        
        for (String size : sizes) {
            for (String length : lengths) {
                ProductVariant variant = new ProductVariant();
                variant.setProduct(product);
                variant.setName(size + "x" + length + " variant");
                variant.setPrice(new BigDecimal(basePrice));
                variant.setColor(color);
                variant.setSize(size + "x" + length);
                variant.setCreatedAt(LocalDateTime.now());
                variant.setStock((int) (Math.random() * 20) + 5); // Random stock between 5 and 25

                productVariantRepository.saveAndFlush(variant);
                
                // Add images with random selection
                for (int i = 0; i < 2; i++) {
                    ProductImage image = new ProductImage();
                    image.setProductVariantId(variant.getId());
                    // Select random image URL
                    int randomIndex = (int) (Math.random() * imageUrls.size());
                    image.setImageUrl(imageUrls.get(randomIndex));
                    image.setIsPrimary(i == 0);
                    image.setCreatedAt(LocalDateTime.now());
                    productImageRepository.saveAndFlush(image);
                }
            }
        }
    }
} 