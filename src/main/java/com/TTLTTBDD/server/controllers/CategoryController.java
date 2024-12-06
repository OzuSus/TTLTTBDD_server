package com.TTLTTBDD.server.controllers;


import com.TTLTTBDD.server.models.dto.CategoryDTO;

import com.TTLTTBDD.server.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {"http://localhost:31415"})
@RequestMapping("api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/id")
    public Optional<CategoryDTO> getCategoryById(@RequestParam int id){
        return categoryService.getCategoryById(id);
    }
}
