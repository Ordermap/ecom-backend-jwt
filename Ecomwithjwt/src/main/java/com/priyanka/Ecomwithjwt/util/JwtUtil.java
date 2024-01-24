package com.priyanka.Ecomwithjwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class JwtUtil {
    private static final String SECRET_KEY="priyanka";
    private static final int TOKEN_VALIDITY=3600*5;
    public String getUserNameFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);

    }
    private <T> T getClaimFromToken(String token, Function<Claims,T>claimsResolver){
        final Claims claims=getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);

    }
    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();

    }
    public boolean validateToken(String token,UserDetails userDetails){
        String userName=getUserNameFromToken(token);
        boolean isValid = userName.equals(userDetails.getUsername()) && (!isTokenExpired(token));
        log.debug("isValid:{}",isValid);
        return isValid;

    }
    private boolean isTokenExpired(String token){
        final Date expirationDate=getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }
    private Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token,Claims::getExpiration);

    }
    public String generateToken(UserDetails userDetails){
        Map<String,Object>claims=new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+TOKEN_VALIDITY*1000))
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
                .compact();

    }

}
