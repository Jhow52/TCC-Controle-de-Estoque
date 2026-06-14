package com.claretiano.estoque.security;

import com.claretiano.estoque.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static com.claretiano.estoque.security.SecurityConstants.*;


@Service
public class JwtService {

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET); // ← Decoders do JJWT
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(User user){
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("roles", user.getRoles().stream().map(Enum::name).toList())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), Jwts.SIG.HS512)
                .compact();
    }

    public String extractUsername(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
    public boolean isTokenValid(String token){
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);

            return true;
        }catch (Exception e){
            System.out.println(">>> TOKEN INVÁLIDO: " + e.getMessage());
            return false;
        }
    }
}
