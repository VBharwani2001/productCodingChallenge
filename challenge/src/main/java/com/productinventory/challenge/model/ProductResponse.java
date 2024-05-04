package com.productinventory.challenge.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class ProductResponse {

    @Id
    private Long id;

    private String name;
    private String description;
    private double price;
    private int quantity;

    public ProductResponse(Long id, String name, String description, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }
}

