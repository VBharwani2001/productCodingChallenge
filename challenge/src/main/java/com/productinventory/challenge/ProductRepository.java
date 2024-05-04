package com.productinventory.challenge;

import com.productinventory.challenge.model.ProductResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductResponse, Long> {
}

