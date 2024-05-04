package com.productinventory.challenge.controller;

import com.productinventory.challenge.exceptions.ProductNotFoundException;
import com.productinventory.challenge.model.ProductResponse;
import com.productinventory.challenge.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public List<ProductResponse> getProducts() {
        logger.info("Retrieving all products");
        return productService.getAllProducts();
    }

    @PostMapping("/products")
    public ResponseEntity<String> addProducts(@RequestBody ProductResponse product) {
        logger.info("Adding product: {}", product);

        // Validate product fields
        if(product.getId() == null){
            logger.error("ID cannot be empty");
            return ResponseEntity.badRequest().body("ID cannot be empty");
        }
        if(product.getName() == null || product.getName().isEmpty()){
            logger.error("Name cannot be empty");
            return ResponseEntity.badRequest().body("Name cannot be empty");
        }
        if(product.getDescription() == null || product.getDescription().isEmpty()){
            logger.error("Description cannot be empty");
            return ResponseEntity.badRequest().body("Description cannot be empty");
        }
        if(product.getPrice() == 0.0){
            logger.error("Price cannot be empty or 0.0");
            return ResponseEntity.badRequest().body("Price cannot be empty or 0.0");
        }
        if(product.getQuantity() == 0){
            logger.error("Quantity cannot be empty");
            return ResponseEntity.badRequest().body("Quantity cannot be empty");
        }

        try {
            productService.addProduct(product);
            logger.info("Product added successfully");
            return ResponseEntity.ok("Product added successfully");
        } catch (Exception e) {
            logger.error("Error adding product: {}", e.getMessage());
            return ResponseEntity.status(500).body("Error adding product: " + e.getMessage());
        }
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        logger.info("Retrieving product with ID: {}", id);
        Optional<ProductResponse> product = productService.getProductById(id);
        if (product.isEmpty()) {
            logger.error("Product with ID {} not found", id);
            return ResponseEntity.notFound().build();
        } else {
            logger.info("Product retrieved successfully: {}", product.get());
            return ResponseEntity.ok(product.get());
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id) {
        logger.info("Deleting product with ID: {}", id);
        try {
            productService.deleteProduct(id);
            logger.info("Product deleted successfully");
            return ResponseEntity.ok("Product deleted successfully");
        } catch (ProductNotFoundException e) {
            logger.error("Product with ID {} not found", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error deleting product: {}", e.getMessage());
            return ResponseEntity.status(500).body("Error deleting product: " + e.getMessage());
        }
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody ProductResponse updatedProduct) {
        logger.info("Updating product with ID: {}", id);
        try {
            productService.updateProduct(id, updatedProduct);
            logger.info("Product updated successfully");
            return ResponseEntity.ok("Product updated successfully");
        } catch (ProductNotFoundException e) {
            logger.error("Product with ID {} not found", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Error updating product: {}", e.getMessage());
            return ResponseEntity.status(500).body("Error updating product: " + e.getMessage());
        }
    }
}
