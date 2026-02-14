package com.example.ecommerce.ecommerce.controller;

import com.example.ecommerce.ecommerce.models.Category;
import com.example.ecommerce.ecommerce.models.Product;
import com.example.ecommerce.ecommerce.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/auth/seller")
public class SellerController {

    @Autowired
    UserInfoRepository userRepo;

    @Autowired
    CartProductRepo cpRepo;

    @Autowired
    CartRepo cartRepo;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    CategoryRepo categoryRepo;

    @PostMapping("/product")
    public ResponseEntity<?> postProduct(@AuthenticationPrincipal Principal principal, @RequestBody Product product)
    {
        String username = principal.getName();
        var user = userRepo.findByUsername(username).orElse(null);
        if(user ==null) return ResponseEntity.status(401).body("Unauthorized user");

        var category = categoryRepo.findById(product.getCategory().getCategoryId()).orElse(null);
        if(category == null) return ResponseEntity.badRequest().body("Invalid category");

        product.setSeller(user);
        product.setCategory(category);
        productRepo.saveAndFlush(product);
        return ResponseEntity.status(201).body("http://localhost:8080/auth/public/product/"+product.getProductId());
    }

    @GetMapping("/product")
    public ResponseEntity<?> AllProducts(@AuthenticationPrincipal Principal principal)
    {
        String username = principal.getName();
        var user = userRepo.findByUsername(username).orElse(null);
        if(user ==null) {
            return ResponseEntity.status(401).body("Unauthorized user");
        }
        List<Product> products= productRepo.findBySellerUserId(user.getUserId());
        return ResponseEntity.ok(products);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getProductById(@AuthenticationPrincipal Principal principal, @PathVariable int productId)
    {
        String username = principal.getName();
        var user = userRepo.findByUsername(username).orElse(null);
        if(user ==null) {
            return ResponseEntity.status(401).body("Unauthorized user");
        }
        var product= productRepo.findBySellerUserIdOrProductId(user.getUserId(), productId);
        if(product.isEmpty()) {
            return ResponseEntity.status(404).body("Product not found");
        }
        return ResponseEntity.ok(product);
    }

    @PutMapping("/product/")
    public ResponseEntity<?> putProduct(@AuthenticationPrincipal Principal principal, @RequestBody Product product) {
        String username = principal.getName();
        var user = userRepo.findByUsername(username).orElse(null);
        if (user == null) {
            return ResponseEntity.status(401).body("Unauthorized user");
        }
        Product prod = productRepo.findBySellerUserIdOrProductId(user.getUserId(), product.getProductId()).orElse(null);
        if (prod == null) {
            return ResponseEntity.status(404).body("Product not found");
        }

        Category category = categoryRepo.findById(product.getCategory().getCategoryId()).orElse(null);
        if(category == null) return ResponseEntity.badRequest().body("Invalid category");

        prod.setProductName(product.getProductName());
        prod.setPrice(product.getPrice());
        prod.setCategory(category);
        productRepo.saveAndFlush(prod);

        return ResponseEntity.ok(prod);
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<?> deleteProduct(@AuthenticationPrincipal Principal principal, @PathVariable int productId)
    {
        String username = principal.getName();
        var user = userRepo.findByUsername(username).orElse(null);
        if(user ==null) {
            return ResponseEntity.status(401).body("Unauthorized user");
        }
        var product= productRepo.findBySellerUserIdOrProductId(user.getUserId(), productId);
        if(product.isEmpty()) {
            return ResponseEntity.status(404).body("Product not found");
        }
        productRepo.delete(product.get());
        return ResponseEntity.ok("Product deleted successfully");
    }


}
