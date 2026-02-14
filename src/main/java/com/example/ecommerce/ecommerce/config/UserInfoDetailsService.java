package com.example.ecommerce.ecommerce.config;

import com.example.ecommerce.ecommerce.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserInfoDetailsService implements UserDetailsService {

    @Autowired
    private UserInfoRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username)
                .map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    }
}
