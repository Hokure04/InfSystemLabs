package org.example.infsyslab1.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTTokenUtil {

    private static final String SECRET_KEY = "ajfkheushflkjrjhtioerhkjgrtjjjjhjigfujylhojkdvjnbherdbfjisrhitohorthgjdfhnc";
    private static final long EXPIRATION_TIME = 86400000; //24 h

    public String generateToken(String username){
        return Jwts.builder()
                .claim("sub", username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, getSigningKey())
                .compact();

    }

    public String getUsernameFromToken(String token){
        try{
            JwtParser jwtParser = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build();

            Claims claims = jwtParser.parseClaimsJws(token).getBody();
            return claims.getSubject();
        } catch (ExpiredJwtException e){
            throw new RuntimeException("JWT token has expired", e);
        }catch (Exception e){
            throw new RuntimeException("Invalid JWT token, e");
        }

    }

    public boolean validateToken(String token, String username){
        String tokenUsername = getUsernameFromToken(token);
        return (username.equals(tokenUsername) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }

    public Claims getClaimsFromToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private byte[] getSigningKey(){
        return SECRET_KEY.getBytes(StandardCharsets.UTF_8);
    }
}
