package com.TTLTTBDD.server.services;

import com.TTLTTBDD.server.models.dto.ProductDTO;
import com.TTLTTBDD.server.models.dto.UserDTO;
import com.TTLTTBDD.server.models.entity.Product;
import com.TTLTTBDD.server.models.entity.User;
import com.TTLTTBDD.server.repositories.ProductRepository;
import com.TTLTTBDD.server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ProductDTO> getProductById(int id) {
        return productRepository.findProductById(id).map(this::convertToDTO);
    }

    public List<ProductDTO> getProductsByCategoryID(int idCategory){
        return productRepository.findByIdCategory_Id (idCategory).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public ProductDTO create(Product product) {
        Product product1 = productRepository.save(product);
        return convertToDTO(product1);
    }
    public void deleteProduct(int idProduct){
        productRepository.deleteById(idProduct);
    }

    private ProductDTO convertToDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrize())
                .quantity(product.getQuantity())
                .description(product.getDescription())
                .image(product.getImage())
                .rating(product.getRating())
                .reviewCount(product.getReview())
                .categoryID(product.getIdCategory().getId())
                .build();
    }


}
