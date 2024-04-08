package com.example.authservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    @Value("${jwt.secretKey}")
    private String key;


    // create jwt from a UserDetail
    public String createToken(UserDetails userDetails){
        System.out.println(key);
        AuthUserDetail authUserDetail = (AuthUserDetail) userDetails;

        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
        claims.put("userId", authUserDetail.getUserId()); // Include the userId in the claims
        claims.put("type", authUserDetail.getType());
        claims.put("active", authUserDetail.isActive());
        claims.put("emailVerified", authUserDetail.isEmailVerified());



        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }
}
