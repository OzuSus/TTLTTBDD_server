package com.TTLTTBDD.server.controllers;

import com.TTLTTBDD.server.models.dto.CategoryDTO;
import com.TTLTTBDD.server.models.dto.ProductDTO;
import com.TTLTTBDD.server.models.dto.UserDTO;
import com.TTLTTBDD.server.models.entity.Product;
import com.TTLTTBDD.server.models.entity.User;
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

    @PostMapping("/createProduct")
    public String create(@RequestBody Product product) {
        try {
            ProductDTO product1 = productService.create(product);
            if (product1 != null) {
                return "Product created successfully";
            }
            return "Product creation failed";
        } catch (Exception e) {
            e.printStackTrace(); // Log chi tiết lỗi
            return "An error occurred: " + e.getMessage();
        }
    }

    @DeleteMapping("/deleteProduct")
    public String deleteProduct(@RequestParam int idProduct) {
        productService.deleteProduct(idProduct);
        return "Deleted successfully";
    }


}
