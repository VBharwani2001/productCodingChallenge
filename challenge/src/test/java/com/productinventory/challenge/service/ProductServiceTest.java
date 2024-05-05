package com.productinventory.challenge.service;
import com.productinventory.challenge.exceptions.ProductNotFoundException;
import com.productinventory.challenge.model.ProductResponse;
import com.productinventory.challenge.repository.ProductRepository;
import com.productinventory.challenge.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    public void testGetAllProducts() {
        // Create a list of products for testing
        List<ProductResponse> productList = new ArrayList<>();
        productList.add(new ProductResponse(1L, "Product 1", "Description 1", 10.0, 100));
        productList.add(new ProductResponse(2L, "Product 2", "Description 2", 20.0, 200));

        // Mock the behavior of the repository to return the list of products when findAll is called
        when(productRepository.findAll()).thenReturn(productList);

        // Call the service method to get all products
        List<ProductResponse> result = productService.getAllProducts();

        // Verify that the result matches the expected list of products
        assertEquals(productList.size(), result.size());
        assertEquals(productList, result);
    }

    @Test
    void testAddProduct() throws ProductNotFoundException {

        ProductResponse newProduct = new ProductResponse(1L, "New Product", "Description", 10.0, 5);
        when(productRepository.findById(newProduct.getId())).thenReturn(Optional.empty());
        when(productRepository.save(newProduct)).thenReturn(newProduct);

        productService.addProduct(newProduct);

        verify(productRepository, times(1)).save(newProduct);

        System.out.println("Mock interactions: " + Mockito.mockingDetails(productRepository).getInvocations());
    }

    @Test
    void testGetProductById() throws ProductNotFoundException {
        Long productId = 1L;
        ProductResponse existingProduct = new ProductResponse(productId, "Existing Product", "Description", 10.0, 5);
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        Optional<ProductResponse> result = productService.getProductById(productId);

        assertEquals(existingProduct, result.get());
    }

    @Test
    void testUpdateProduct() throws ProductNotFoundException {
        Long productId = 1L;
        ProductResponse existingProduct = new ProductResponse(productId, "Existing Product", "Description", 10.0, 5);
        ProductResponse updatedProduct = new ProductResponse(productId, "Updated Product", "Updated Description", 20.0, 10);
        when(productRepository.existsById(productId)).thenReturn(true);
        when(productRepository.save(updatedProduct)).thenReturn(updatedProduct);

        productService.updateProduct(productId, updatedProduct);

        verify(productRepository, times(1)).save(updatedProduct);
    }
}
