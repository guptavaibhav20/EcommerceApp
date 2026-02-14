package com.example.ecommerce.ecommerce.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"cart_id", "product_id"}))
@Data
public class CartProduct {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int cpId;

    @Column (name= "cart_id", insertable = false, updatable = false)
    private int cartId;

    @Column (name= "product_id", insertable = false, updatable = false)
    private int productId;

    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "cartId")
    @JsonIgnore
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "productId")
    private Product product;

    private  Integer quantity = 1;

    public CartProduct()
    {
        super();
    }

    public CartProduct(int cartId, int productId, Integer quantity) {
        this.cartId = cartId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public CartProduct(Cart cart, Product product, Integer quantity) {
        super();
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }
}
