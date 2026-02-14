package com.example.ecommerce.ecommerce.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Entity
@AllArgsConstructor
@Data
public class Cart {

    public Cart(int cartId, double totalAmount) {
        this.cartId = cartId;
        this.totalAmount = totalAmount;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int cartId;

    private double totalAmount;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    @JsonIgnore
    private UserInfo user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "cart")
    private List<CartProduct> cartProducts;

    public Cart(){
        super();
    }

    public void updateTotalAmount(Double amount)
    {
        this.totalAmount += amount;
    }

}
