package com.example.history.secutiry;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secretKey}")
    // TODO: change after the real key in auth service be implemented
    private String secretKey;

    public JwtPayload parseToken(String token) {
        if (token != null && token.startsWith("Bearer")) {
            token = token.substring(7);
        }
        Jws<Claims> jwsClaims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token);

        Claims claims = jwsClaims.getBody();

        Long userId = claims.get("userId", Long.class);
        String userType = claims.get("type", String.class);
        boolean isEmailVerified = claims.get("emailVerified", Boolean.class);
        if (userType.equals("admin")) {
            userType = "ADMIN_USER";
        }
        else if (isEmailVerified) {
            userType = "UNVERIFIED_USER";
        }
        else {
            userType = "NORMAL_USER";
        }

        // Extract permissions
//        String userType = "";
//        List<Map<String, String>> permissions = claims.get("permissions", List.class);
//        for (Map<String, String> permission : permissions) {
//            if (permission.containsKey("authority")) {
//                userType = permission.get("authority");
//                break;
//            }
//        }

        return new JwtPayload(userId, userType);
    }

}

