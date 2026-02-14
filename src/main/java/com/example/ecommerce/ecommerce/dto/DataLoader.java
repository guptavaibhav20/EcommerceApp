//// java
//package com.example.ecommerce.ecommerce.dto;
//
//import com.example.ecommerce.ecommerce.models.*;
//import com.example.ecommerce.ecommerce.repository.*;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Arrays;
//import java.util.Collections;
//
//@Component
//public class DataLoader implements CommandLineRunner {
//
//    private final UserInfoRepository userRepo;
//    private final CategoryRepo categoryRepo;
//    private final ProductRepo productRepo;
//    private final CartRepo cartRepo;
//    private final CartProductRepo cartProductRepo;
//
//    public DataLoader(UserInfoRepository userRepo,
//                      CategoryRepo categoryRepo,
//                      ProductRepo productRepo,
//                      CartRepo cartRepo,
//                      CartProductRepo cartProductRepo) {
//        this.userRepo = userRepo;
//        this.categoryRepo = categoryRepo;
//        this.productRepo = productRepo;
//        this.cartRepo = cartRepo;
//        this.cartProductRepo = cartProductRepo;
//    }
//
//    @Override
//    @Transactional
//    public void run(String... args) throws Exception {
//        // assume userRepo, categoryRepo, productRepo already saved as in your code
//
//        UserInfo seller = new UserInfo("seller1", "password", "ROLE_SELLER");
//        UserInfo buyer  = new UserInfo("buyer1", "password", "ROLE_USER");
//        userRepo.saveAll(Arrays.asList(seller, buyer));
//
//        Category electronics = new Category(); electronics.setCategoryName("Electronics");
//        Category books = new Category(); books.setCategoryName("Books");
//        categoryRepo.saveAll(Arrays.asList(electronics, books));
//
//        Product phone = new Product("Smartphone", 499.99, seller, electronics);
//        Product novel = new Product("Novel Book", 19.99, seller, books);
//        productRepo.saveAll(Arrays.asList(phone, novel));
//
//        // create cart and children, set both sides
//        Cart cart = new Cart();
//        cart.setUser(buyer);
//        cart.setTotalAmount(0.0);
//
//        CartProduct cp1 = new CartProduct();
//        cp1.setCart(cart);            // owning side
//        cp1.setProduct(phone);
//        cp1.setQuantity(2);
//
//        CartProduct cp2 = new CartProduct();
//        cp2.setCart(cart);
//        cp2.setProduct(novel);
//        cp2.setQuantity(1);
//
//        // add children into parent's collection (non-owning side)
//        cart.setCartProducts(Arrays.asList(cp1, cp2));
//        // or: cart.getCartProducts().add(cp1); cart.getCartProducts().add(cp2);
//
//        // compute total
//        double total = phone.getPrice() * cp1.getQuantity() + novel.getPrice() * cp2.getQuantity();
//        cart.setTotalAmount(total);
//
//        // single save (requires cascade = ALL on cart.cartProducts)
//        cartRepo.save(cart);
//
//        System.out.println("Seeded cart with items (cascade save).");
//    }
//
//}
