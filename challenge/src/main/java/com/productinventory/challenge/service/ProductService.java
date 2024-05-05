package com.productinventory.challenge.service;

import com.productinventory.challenge.exceptions.ProductNotFoundException;
import com.productinventory.challenge.model.ProductResponse;
import com.productinventory.challenge.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll();
    }

    public void addProduct(ProductResponse product) throws ProductNotFoundException {
        validateProductFields(product);
        Optional<ProductResponse> existingProduct = getProductById(product.getId());
        if (existingProduct.isPresent()) {
            throw new IllegalArgumentException("Product with ID " + product.getId() + " already exists");
        }
        productRepository.save(product);
    }


    public Optional<ProductResponse> getProductById(Long id) throws ProductNotFoundException {
            return productRepository.findById(id);
    }

    public void deleteProduct(Long id) throws ProductNotFoundException {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product with ID " + id + " does not exist");
        }
        productRepository.deleteById(id);
    }

    public void updateProduct(Long id, ProductResponse updatedProduct) throws ProductNotFoundException {
        validateProductFields(updatedProduct);
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product with ID " + id + " does not exist");
        }
        updatedProduct.setId(id); // Ensure ID consistency
        productRepository.save(updatedProduct);
    }

    private void validateProductFields(ProductResponse product) {
        if (product.getId() == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (product.getName() == null || product.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (product.getDescription() == null || product.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        if (product.getPrice() == 0.0) {
            throw new IllegalArgumentException("Price cannot be empty or 0.0");
        }
        if (product.getQuantity() == 0) {
            throw new IllegalArgumentException("Quantity cannot be empty");
        }
    }
}
