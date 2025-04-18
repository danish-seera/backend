package com.kabirclub.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItem {
    private String title;
    private String path;
    private List<MenuItem> items = new ArrayList<>();
} 