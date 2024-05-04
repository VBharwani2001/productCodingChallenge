package com.productinventory.challenge.controller;

import com.productinventory.challenge.exceptions.ProductNotFoundException;
import com.productinventory.challenge.model.ProductResponse;
import com.productinventory.challenge.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetProducts() {
        List<ProductResponse> productList = new ArrayList<>();
        productList.add(new ProductResponse(1L, "Product 1", "Description 1", 10.0, 100));
        productList.add(new ProductResponse(2L, "Product 2", "Description 2", 20.0, 200));

        when(productService.getAllProducts()).thenReturn(productList);

        List<ProductResponse> result = productController.getProducts();

        assertEquals(productList.size(), result.size());
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    public void testAddProduct() {
        ProductResponse product = new ProductResponse(1L, "Product 1", "Description 1", 10.0, 100);
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Product added successfully");

        when(productService.getProductById(1L)).thenReturn(Optional.empty());
        ResponseEntity<String> result = productController.addProducts(product);

        assertEquals(expectedResponse, result);
        verify(productService, times(1)).addProduct(product);
    }

    @Test
    public void testGetProductById() {
        Long productId = 1L;
        ProductResponse product = new ProductResponse(productId, "Product 1", "Description 1", 10.0, 100);

        when(productService.getProductById(productId)).thenReturn(Optional.of(product));

        ResponseEntity<ProductResponse> result = productController.getProductById(productId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(product, result.getBody());
    }

    @Test
    public void testDeleteProductById() throws ProductNotFoundException {
        Long productId = 1L;
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Product deleted successfully");

        doNothing().when(productService).deleteProduct(productId);

        ResponseEntity<String> result = productController.deleteProductById(productId);

        assertEquals(expectedResponse, result);
        verify(productService, times(1)).deleteProduct(productId);
    }

    @Test
    public void testUpdateProduct() throws ProductNotFoundException {
        Long productId = 1L;
        ProductResponse updatedProduct = new ProductResponse(productId, "Product 1", "Updated Description", 15.0, 150);
        ResponseEntity<String> expectedResponse = ResponseEntity.ok("Product updated successfully");

        doNothing().when(productService).updateProduct(productId, updatedProduct);

        ResponseEntity<String> result = productController.updateProduct(productId, updatedProduct);

        assertEquals(expectedResponse, result);
        verify(productService, times(1)).updateProduct(productId, updatedProduct);
    }
}
