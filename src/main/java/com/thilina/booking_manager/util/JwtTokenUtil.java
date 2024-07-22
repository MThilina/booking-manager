package com.thilina.booking_manager.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenUtil {

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.expiration}")
    @Getter private Long jwtExpiration;

    public String generateToken(String username) {
        byte[] encodedSecretKey = Base64.getDecoder().decode(secret);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(Keys.hmacShaKeyFor(encodedSecretKey))
                .compact();
    }

    public String getUsernameFromToken(String token) {
        byte[] encodedSecretKey = Base64.getDecoder().decode(secret);
        return Jwts.parserBuilder().setSigningKey(encodedSecretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        byte[] encodedSecretKey = Base64.getDecoder().decode(secret);
        try {
            Jwts.parserBuilder().setSigningKey(encodedSecretKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getTokenExpiryDate() {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + this.getJwtExpiration());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(expiryDate);
    }

    // Test Data saved as Base64 that's why this unsafe method develop
    // 1st part of the data chunk is user
    public String getUsernameFromBase64encoding(String token) {
        byte[] decodedSecretKey = Base64.getDecoder().decode(token);
        return new String(decodedSecretKey).split(":")[0];
    }
}
