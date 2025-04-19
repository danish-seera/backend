package com.kabirclub.controller;

import com.kabirclub.model.MenuItem;
import com.kabirclub.model.MenuResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MenuController {

    @GetMapping("/menu")
    public ResponseEntity<MenuResponse> getMenu() {
        log.info("getMenu called");
        
        List<MenuItem> menuItems = new ArrayList<>();
        
        // Home menu item
        
        // Products menu item
        menuItems.add(new MenuItem("All Products", "/search", new ArrayList<>()));
        
        // About menu item
        menuItems.add(new MenuItem("Topwear", "/search?category=topwear", new ArrayList<>()));
        menuItems.add(new MenuItem("Bottomwear", "/search?category=bottomwear", new ArrayList<>()));
        menuItems.add(new MenuItem("Fragrances", "/search?category=fragrances", new ArrayList<>()));
        menuItems.add(new MenuItem("Contact", "/contact", new ArrayList<>()));
        MenuResponse menuResponse = new MenuResponse(menuItems);
        
        return ResponseEntity.ok(menuResponse);
    }
} 