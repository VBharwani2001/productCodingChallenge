package com.productinventory.challenge.service;

import com.productinventory.challenge.ProductRepository;
import com.productinventory.challenge.exceptions.ProductNotFoundException;
import com.productinventory.challenge.model.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    List<ProductResponse> products = new ArrayList<>();

    @Autowired
    private ProductRepository productRepository;

    public ProductService() {
        // Initialize products with some dummy data
        products.add(new ProductResponse(1L, "Product 1", "Description 1", 11.11, 111));
        products.add(new ProductResponse(2L, "Product 2", "Description 2", 22.22, 222));
        products.add(new ProductResponse(3L, "Product 3", "Description 3", 33.33, 333));
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll();
    }

    public void addProduct(ProductResponse product) {
        try {
            Optional<ProductResponse> existingProduct = getProductById(product.getId());
            if (existingProduct.isPresent()) {
                throw new Exception("Product with ID already exists");
            } else {
                products.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Optional<ProductResponse> getProductById(Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    public void deleteProduct(Long id)  throws ProductNotFoundException{
        // Find the product with the specified ID
        Optional<ProductResponse> productToDelete = getProductById(id);

        // If the product is not found, throw ProductNotFoundException
        if (productToDelete.isEmpty()) {
            throw new ProductNotFoundException("Product with ID " + id + " does not exist");
        }

        products.remove(products.remove(productToDelete.get()));
    }

    //need to write new class for updateProduct so that it should not update the ID
    public void updateProduct(Long id, ProductResponse updatedProduct) throws ProductNotFoundException {
        // Find the product with the specified ID
        Optional<ProductResponse> productToUpdate = getProductById(id);

        // If the product is not found, throw ProductNotFoundException
        if (productToUpdate.isEmpty()) {
            throw new ProductNotFoundException("Product with ID " + id + " does not exist");
        }

        // Update the fields of the existing product with the new values
        ProductResponse existingProduct = productToUpdate.get();
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setQuantity(updatedProduct.getQuantity());

    }
}
