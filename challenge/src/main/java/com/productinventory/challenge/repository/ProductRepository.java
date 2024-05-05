package com.productinventory.challenge.repository;

import com.productinventory.challenge.model.ProductResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductResponse, Long> {
}

