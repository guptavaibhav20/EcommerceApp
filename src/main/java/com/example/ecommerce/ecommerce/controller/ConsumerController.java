package com.example.ecommerce.ecommerce.controller;


import com.example.ecommerce.ecommerce.models.Cart;
import com.example.ecommerce.ecommerce.models.CartProduct;
import com.example.ecommerce.ecommerce.models.Product;
import com.example.ecommerce.ecommerce.repository.CartProductRepo;
import com.example.ecommerce.ecommerce.repository.CartRepo;
import com.example.ecommerce.ecommerce.repository.ProductRepo;
import com.example.ecommerce.ecommerce.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/auth/consumer")
public class ConsumerController {

    @Autowired
    UserInfoRepository userRepo;

    @Autowired
    CartProductRepo cpRepo;

    @Autowired
    CartRepo cartRepo;

    @Autowired
    ProductRepo productRepo;

    @GetMapping("/cart")
    public ResponseEntity<?> getCart(@AuthenticationPrincipal Principal principal)
    {
        try {
            String username = principal.getName();
            Cart cart = cartRepo.findByUserUsername(username).orElse(null);
            return ResponseEntity.ok(cart);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/cart")
    public ResponseEntity<?> postCart(@AuthenticationPrincipal Principal principal, @RequestBody Product product)
    {
        String username = principal.getName();
        Cart cart = cartRepo.findByUserUsername(username).orElse(null);
        Product prod = productRepo.findById(product.getProductId()).orElse(null);
        if(cart.getCartProducts().size()>0 && cart.getCartProducts().stream().anyMatch(n-> n.getProduct().equals(prod)))
        {
            CartProduct cp = new CartProduct();
            cp.setCart(cart);
            if(prod!=null)
                prod.setProductName(product.getProductName());
            cp.setProduct(prod);
            cp.setQuantity(1);
            cart.getCartProducts().add(cp);
            cart.updateTotalAmount(prod.getPrice() * cp.getQuantity());
            cartRepo.save(cart);
            return ResponseEntity.ok(cart);
        }

        return ResponseEntity.status(409).build();
    }

    @PutMapping("/cart")
    public ResponseEntity<?> putCart(@AuthenticationPrincipal Principal principal, @RequestBody CartProduct cartProduct)
    {
        String username = principal.getName();
        Cart cart = cartRepo.findByUserUsername(username).orElse(null);
        if(cart != null)
        {
            return ResponseEntity.badRequest().body("cart not found");
        }
        Product prod = productRepo.findById(cartProduct.getProduct().getProductId()).orElse(null);
        if(prod != null)
        {
            return ResponseEntity.badRequest().body("Product not found");
        }

        CartProduct cp = cart.getCartProducts().stream().filter(n-> n.getProduct().equals(prod)).findFirst().orElse(null);
        if(cp == null) {
            if(cartProduct.getQuantity() > 0)
            {
                cp = new CartProduct();
                prod.setProductName(cartProduct.getProduct().getProductName());
                cp.setProduct(prod);
                cp.setQuantity(cartProduct.getQuantity());
                cart.updateTotalAmount(prod.getPrice() * cartProduct.getQuantity());
                cp.setCart(cart);
                cart.getCartProducts().add(cp);
            }
        } else{
            if(cartProduct.getQuantity() == 0)
            {
                cart.getCartProducts().remove(cp);
                cart.updateTotalAmount(-prod.getPrice() * cp.getQuantity());
                cpRepo.delete(cp);
            }
            else{
                prod.setProductName(cartProduct.getProduct().getProductName());
                cart.updateTotalAmount(prod.getPrice() * (cartProduct.getQuantity() - cp.getQuantity()));
                cart.getCartProducts().get(cart.getCartProducts().indexOf(cp)).setQuantity(cartProduct.getQuantity());
            }
        }
            cartRepo.saveAndFlush(cart);
            return ResponseEntity.ok(cart);

    }

    @DeleteMapping("/cart")
    public ResponseEntity<?> deletecart(@AuthenticationPrincipal Principal principal, @RequestBody Product product)
    {
        String username = principal.getName();
        Cart cart = cartRepo.findByUserUsername(username).orElse(null);
        if(cart != null)
        {
            return ResponseEntity.badRequest().body("cart not found");
        }
        CartProduct cp = cart.getCartProducts().stream().filter(n-> n.getProduct().equals(product)).findFirst().orElse(null);

        if(cp != null)
        {
            return ResponseEntity.badRequest().body("product not found");
        }
        cart.getCartProducts().remove(cp);
        cart.updateTotalAmount(-cp.getProduct().getPrice() * cp.getQuantity());
        cpRepo.delete(cp);
        cartRepo.save(cart);
        return ResponseEntity.ok(cart);
    }

}
