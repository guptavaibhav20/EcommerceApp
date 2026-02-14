package com.example.ecommerce.ecommerce.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
public class JwtService {

    private final String SECRET_KEY = "bXl2ZXJ5c2VjdXJlc2VjcmV0a2V5MTIzNDU2Nzg5MA==";

    @SuppressWarnings("deprecation")
    public Claims extractAllClaims(String token)
    {
        return Jwts.parser()
                .verifyWith(getSignedKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    public String extractUsername(String token)
    {
        return extractAllClaims(token).getSubject();
    }

    public Date extractExpiration(String token)
    {
        return extractAllClaims(token).getExpiration();
    }

    public Boolean isTokenExpired(String token)
    {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails)
    {
        String username = extractUsername(token);
        Date expiry = extractExpiration(token);
        return username.equals(userDetails.getUsername()) && !expiry.before(new Date());

    }

    public String generateToken(String username)
    {
        HashMap<String, Object> claims = new HashMap<>();
        return createToken(claims,username);
    }

    private String createToken(HashMap<String, Object> claims, String username) {
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*30))
                .signWith(getSignedKey())
                .compact();
    }


    public SecretKey getSignedKey()
    {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }
    // Use SECRET_KEY.getBytes(); when key is not in a BASE64 Encoded key

}

