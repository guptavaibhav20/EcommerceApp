package com.example.ecommerce.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Product {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int productId;

    private String productName;

    private double price;

    @ManyToOne
    @JoinColumn(name = "seller_id", referencedColumnName = "userId", updatable = false)
    @JsonIgnore
    private UserInfo seller;


    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "categoryId")
    private Category category;

    public Product() {
        super();
    }

    public Product(String productName, double price, UserInfo seller, Category category) {
        this.productName = productName;
        this.price = price;
        this.seller = seller;
        this.category = category;
    }

}
