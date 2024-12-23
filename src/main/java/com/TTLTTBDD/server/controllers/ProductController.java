package com.TTLTTBDD.server.controllers;

import com.TTLTTBDD.server.models.dto.CategoryDTO;
import com.TTLTTBDD.server.models.dto.ProductDTO;
import com.TTLTTBDD.server.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {"http://localhost:31415"})
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/id")
    public Optional<ProductDTO> getProductById(@RequestParam int id){
        return productService.getProductById(id);
    }

    @GetMapping("/idCategory")
    public List<ProductDTO> getProductsByCategoryID(@RequestParam int idCategory){
        return productService.getProductsByCategoryID(idCategory);
    }

}
