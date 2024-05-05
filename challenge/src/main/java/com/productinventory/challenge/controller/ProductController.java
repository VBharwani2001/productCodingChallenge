package com.productinventory.challenge.controller;
import com.productinventory.challenge.exceptions.ProductNotFoundException;
import com.productinventory.challenge.model.ProductResponse;
import com.productinventory.challenge.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductResponse> getProducts() {
        logger.info("Retrieving all products");
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) throws ProductNotFoundException {
        logger.info("Retrieving product with ID: {}", id);
        ProductResponse product = productService.getProductById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));
        logger.info("Product retrieved successfully: {}", product);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductResponse updatedProduct) throws ProductNotFoundException {
        logger.info("Updating product with ID: {}", id);
        productService.updateProduct(id, updatedProduct);
        logger.info("Product updated successfully");
        return ResponseEntity.ok("Product updated successfully");
    }

    @PostMapping
    public ResponseEntity<String> addProduct(@Valid @RequestBody ProductResponse product) throws ProductNotFoundException {
        logger.info("Adding product: {}", product);
        productService.addProduct(product);
        logger.info("Product added successfully");
        return ResponseEntity.ok("Product added successfully");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id) throws ProductNotFoundException {
        logger.info("Deleting product with ID: {}", id);
        productService.deleteProduct(id);
        logger.info("Product deleted successfully");
        return ResponseEntity.ok("Product deleted successfully");
    }

}
