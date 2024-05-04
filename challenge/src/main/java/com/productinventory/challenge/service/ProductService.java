package com.productinventory.challenge.service;

import com.productinventory.challenge.ProductRepository;
import com.productinventory.challenge.exceptions.ProductNotFoundException;
import com.productinventory.challenge.model.ProductResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);


    public ProductService() {}

    public List<ProductResponse> getAllProducts() {
        logger.info("Retrieving all products");
        return productRepository.findAll();
    }

    public void addProduct(ProductResponse product) {
        logger.info("Adding product: {}", product);

        if (productRepository.existsById(product.getId())) {
            logger.error("Product with ID {} already exists", product.getId());
            throw new RuntimeException("Product with ID already exists");
        }
        productRepository.save(product);
        logger.info("Product added successfully");
    }

    public Optional<ProductResponse> getProductById(Long id) {
        logger.info("Retrieving product with ID: {}", id);
        return productRepository.findById(id);
    }

    public void deleteProduct(Long id)  throws ProductNotFoundException{
        logger.info("Deleting product with ID: {}", id);
        if (!productRepository.existsById(id)) {
            logger.error("Product with ID {} does not exist", id);
            throw new ProductNotFoundException("Product with ID " + id + " does not exist");
        }
        productRepository.deleteById(id);
        logger.info("Product deleted successfully");
    }

    //need to write new class for updateProduct so that it should not update the ID
    public void updateProduct(Long id, ProductResponse updatedProduct) throws ProductNotFoundException {
        logger.info("Updating product with ID: {}", id);
        if (!productRepository.existsById(id)) {
            logger.error("Product with ID {} does not exist", id);
            throw new ProductNotFoundException("Product with ID " + id + " does not exist");
        }

        updatedProduct.setId(id);

        productRepository.save(updatedProduct);
        logger.info("Product updated successfully");
    }
}
