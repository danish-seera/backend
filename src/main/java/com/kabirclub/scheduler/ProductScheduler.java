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
public class ProductScheduler {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductVariantRepository productVariantRepository;

    private final List<String> shirtColors = Arrays.asList(
        "White", "Black", "Blue", "Red", "Green", "Yellow", "Gray"
    );

    private final List<String> shirtStyles = Arrays.asList(
        "Classic Fit", "Slim Fit", "Regular Fit", "Oversized", "V-Neck", "Polo"
    );

    private final List<String> shirtBrands = Arrays.asList(
        "Nike", "Adidas", "Puma", "Levi's", "H&M", "Zara", "Uniqlo"
    );

    private final List<String> imageUrls = Arrays.asList(
        // T-Shirts
        "https://images.unsplash.com/photo-1527719327859-c6ce80353573?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTB8fHRzaGlydHxlbnwwfHwwfHx8MA%3D%3D",
        "https://images.unsplash.com/photo-1596755094514-f87e34085b2c?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NHx8c2hpcnR8ZW58MHx8MHx8fDA%3D",
        "https://plus.unsplash.com/premium_photo-1678218594563-9fe0d16c6838?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NXx8c2hpcnR8ZW58MHx8MHx8fDA%3D",
        "https://images.unsplash.com/photo-1607345366928-199ea26cfe3e?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTB8fHNoaXJ0fGVufDB8fDB8fHww",
        "https://images.unsplash.com/photo-1578587018452-892bacefd3f2?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTZ8fHNoaXJ0fGVufDB8fDB8fHww",
        
        // Polo Shirts
        "https://images.unsplash.com/photo-1548778943-5bbeeb1ba6c1?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MjR8fHNoaXJ0fGVufDB8fDB8fHww",
        "https://images.unsplash.com/photo-1620012253295-c15cc3e65df4?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MjN8fHNoaXJ0fGVufDB8fDB8fHww",
        "https://images.unsplash.com/photo-1604695573706-53170668f6a6?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MzV8fHNoaXJ0fGVufDB8fDB8fHww",
        
        // Casual Shirts
        "https://images.unsplash.com/photo-1589310243389-96a5483213a8?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MzZ8fHNoaXJ0fGVufDB8fDB8fHww",
        "https://images.unsplash.com/photo-1563389234808-52344934935c?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NDR8fHNoaXJ0fGVufDB8fDB8fHww",
        "https://images.unsplash.com/photo-1554568218-0f1715e72254?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NjZ8fHNoaXJ0fGVufDB8fDB8fHww",
        
        // Formal Shirts
        "https://images.unsplash.com/photo-1608234807905-4466023792f5?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTAyfHxzaGlydHxlbnwwfHwwfHx8MA%3D%3D",
        "https://images.unsplash.com/photo-1618001789196-8b986847cd5e?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTEwfHxzaGlydHxlbnwwfHwwfHx8MA%3D%3D",
        
        // Oversized Shirts
        "https://images.unsplash.com/photo-1622445275649-b1922cc3e837?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1yZWxhdGVkfDN8fHxlbnwwfHx8fHw%3D",
        "https://images.unsplash.com/photo-1616006897093-5e4635c0de35?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1yZWxhdGVkfDExOHx8fGVufDB8fHx8fA%3D%3D",
        
        // Graphic Tees
        "https://images.unsplash.com/photo-1527719327859-c6ce80353573?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTB8fHRzaGlydHxlbnwwfHwwfHx8MA%3D%3D",
        "https://images.unsplash.com/photo-1596755094514-f87e34085b2c?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NHx8c2hpcnR8ZW58MHx8MHx8fDA%3D",
        "https://plus.unsplash.com/premium_photo-1678218594563-9fe0d16c6838?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NXx8c2hpcnR8ZW58MHx8MHx8fDA%3D"
    );

    @Scheduled(fixedRate = 10*1000)
    public void addNewShirt() {
        log.info("Adding new shirt");
        // Generate random shirt details
        String brand = shirtBrands.get((int) (Math.random() * shirtBrands.size()));
        String style = shirtStyles.get((int) (Math.random() * shirtStyles.size()));
        String color = shirtColors.get((int) (Math.random() * shirtColors.size()));
        Double basePrice = 19 + (Math.random() * 30); // Random price between 19.99 and 49.99

        // Create product
        Product product = new Product();
        product.setTitle(brand + " " + style + " " + color + " T-Shirt");
        product.setDescription("High quality " + style.toLowerCase() + " " + color.toLowerCase() + " t-shirt from " + brand);
        product.setPrice(new BigDecimal(basePrice));
        product.setCategory("Topwear");
        product = productRepository.saveAndFlush(product);

        // Add variants
        List<String> sizes = Arrays.asList("S", "M", "L", "XL");
        for (String size : sizes) {
            ProductVariant variant = new ProductVariant();
            variant.setProduct(product);
            variant.setName(size + " variant");
            variant.setPrice(new BigDecimal(basePrice));
            variant.setColor(color);
            variant.setSize(size);
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