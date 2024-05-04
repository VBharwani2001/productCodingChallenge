package com.productinventory.challenge.controller;
import com.productinventory.challenge.exceptions.ProductNotFoundException;
import com.productinventory.challenge.model.ProductResponse;
import com.productinventory.challenge.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public List<ProductResponse> getProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/products")
    public ResponseEntity<String> addProducts(ProductResponse product) {

        if(product.getId() == null){
            return ResponseEntity.badRequest().body("ID cannot be empty");
        }
        if(product.getName() == "" || product.getName() == null){
            return ResponseEntity.badRequest().body("Name cannot be empty");
        }
        if(product.getDescription() == "" || product.getDescription() == null){
            return ResponseEntity.badRequest().body("Description cannot be empty");
        }
        if(product.getPrice() == 0.0){
            return ResponseEntity.badRequest().body("Price cannot be empty or 0.0");
        }
        if(product.getQuantity() == 0){
            return ResponseEntity.badRequest().body("Quantity cannot be empty");
        }
        // Check if the product ID already exists
        if (productService.getProductById(product.getId()).isPresent()) {
            return ResponseEntity.badRequest().body("Product with the given ID already exists");
        }

        // If all checks pass, add the product
        productService.addProduct(product);
        return ResponseEntity.ok("Product added successfully");
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {

        Optional<ProductResponse> product = productService.getProductById(id);
        if (product.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(product.get());
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok("Product deleted successfully");
        } catch (ProductNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody ProductResponse updatedProduct) {
        try {
            productService.updateProduct(id, updatedProduct);
            return ResponseEntity.ok("Product updated successfully");
        } catch (ProductNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}