package com.productinventory.challenge.service;

import com.productinventory.challenge.ProductRepository;
import com.productinventory.challenge.exceptions.ProductNotFoundException;
import com.productinventory.challenge.model.ProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllProducts() {
        List<ProductResponse> productList = new ArrayList<>();
        productList.add(new ProductResponse(1L, "Product 1", "Description 1", 10.0, 100));
        productList.add(new ProductResponse(2L, "Product 2", "Description 2", 20.0, 200));

        when(productRepository.findAll()).thenReturn(productList);

        List<ProductResponse> result = productService.getAllProducts();

        assertEquals(productList.size(), result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void testAddProduct() {
        ProductResponse product = new ProductResponse(1L, "Product 1", "Description 1", 10.0, 100);

        when(productRepository.existsById(1L)).thenReturn(false);

        productService.addProduct(product);

        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testGetProductById() {
        Long productId = 1L;
        ProductResponse product = new ProductResponse(productId, "Product 1", "Description 1", 10.0, 100);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Optional<ProductResponse> result = productService.getProductById(productId);

        assertEquals(product, result.get());
    }

    @Test
    public void testDeleteProduct() throws ProductNotFoundException {
        Long productId = 1L;

        when(productRepository.existsById(productId)).thenReturn(true);

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    public void testUpdateProduct() throws ProductNotFoundException {
        Long productId = 1L;
        ProductResponse updatedProduct = new ProductResponse(productId, "Product 1", "Updated Description", 15.0, 150);

        when(productRepository.existsById(productId)).thenReturn(true);

        productService.updateProduct(productId, updatedProduct);

        verify(productRepository, times(1)).save(updatedProduct);
    }

    @Test
    public void testDeleteProductNotFoundException() {
        Long productId = 1L;

        when(productRepository.existsById(productId)).thenReturn(false);

        try {
            productService.deleteProduct(productId);
        } catch (ProductNotFoundException e) {
            return;
        }

        assertEquals(true, false, "ProductNotFoundException should have been thrown");
    }
}
