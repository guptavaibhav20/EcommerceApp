package com.example.ecommerce.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Category {

    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Id
    @JsonIgnore
    private int categoryId;

    @Column(unique = true)
    private String categoryName;

    public Category() {
        super();
    }
}
