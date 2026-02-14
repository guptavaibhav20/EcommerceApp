package com.example.ecommerce.ecommerce.controller;

import com.example.ecommerce.ecommerce.dto.AuthRequest;
import com.example.ecommerce.ecommerce.models.Cart;
import com.example.ecommerce.ecommerce.models.UserInfo;
import com.example.ecommerce.ecommerce.repository.CartRepo;
import com.example.ecommerce.ecommerce.repository.UserInfoRepository;
import com.example.ecommerce.ecommerce.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/public")
public class LoginController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserInfoRepository userRepo;

    @Autowired
    private CartRepo cartRepo;

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody AuthRequest authRequest)
    {
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

            String token = jwtService.generateToken(authRequest.getUsername());

            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid credentials"+e);
        }

    }

    @PostMapping("/register")
    public void register(@RequestBody UserInfo request) {

        if (userRepo.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        UserInfo user = new UserInfo();
        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRoles(request.getRoles());

        userRepo.save(user);

        // create cart ONLY for consumer
        if ("CONSUMER".equals(request.getRoles())) {
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setTotalAmount(0.0);
            cartRepo.save(cart);
        }
    }
}
