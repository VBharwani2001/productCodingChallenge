package com.productinventory.challenge.service;

import com.productinventory.challenge.exceptions.ProductNotFoundException;
import com.productinventory.challenge.model.ProductResponse;
import com.productinventory.challenge.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceNegativeTests {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void testAddProductWithExistingId() {
        ProductResponse existingProduct = new ProductResponse(1L, "Existing Product", "Description", 10.0, 5);
        when(productRepository.findById(existingProduct.getId())).thenReturn(Optional.of(existingProduct));

        assertThrows(IllegalArgumentException.class, () -> productService.addProduct(existingProduct));
    }



    @Test
    void testDeleteNonExistentProduct() {
        Long nonExistentId = 100L;
        when(productRepository.existsById(nonExistentId)).thenReturn(false);

        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(nonExistentId));
    }

    @Test
    void testUpdateNonExistentProduct() {
        Long nonExistentId = 100L;
        ProductResponse updatedProduct = new ProductResponse(nonExistentId, "Updated Product", "Description", 10.0, 5);
        when(productRepository.existsById(nonExistentId)).thenReturn(false);

        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(nonExistentId, updatedProduct));
    }

}
