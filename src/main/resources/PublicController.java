package com.example.ecommerce.ecommerce.controller;

import com.example.ecommerce.ecommerce.models.Product;
import com.example.ecommerce.ecommerce.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth/public")
public class PublicController {

    @Autowired
    ProductRepo productRepo;

    @GetMapping("/search/product")
    public ResponseEntity<?> getProductByKeyword(@RequestParam String keyword)
    {
        try {
            List<Product> products = productRepo.findByProductNameContainingIgnoreCaseOrCategoryCategoryNameContainingIgnoreCase(keyword,keyword);

            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Bad Request from keyword product search");
        }
    }
}
