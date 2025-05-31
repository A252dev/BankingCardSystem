package com.cards.cards.services;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.cards.cards.dao.LoginUserDAO;
import com.cards.cards.models.UserModel;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

    private String secretKey = null;
    private SecretKey validateKey = null;

    // @Autowired
    // private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public JwtService() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGen.generateKey();
            validateKey = sk;
            secretKey = java.util.Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String generateToken(LoginUserDAO user) {
        UserModel findUser;
        if (user.getEmail().isPresent())
            findUser = userService.getUserEmailFromDatabase(user.getEmail().get()).getUser_id();
        findUser = userService.getUserEmailByPhone(user.getPhone().get()).getUser_id();
        if (findUser != null
                && passwordEncoder.matches(user.getPassword(),
                        findUser.getPassword())) {
            Map<String, Object> claims = new HashMap<>();

            return Jwts.builder()
                    .claims(claims)
                    .subject(findUser.getId().toString())
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 60 * 24))
                    .signWith(getKey())
                    .compact();
        } else {
            return null;
        }
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        if (claims == null)
            return null;
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(validateKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception exception) {
            System.out.println("Error:" + exception.getMessage());
            return null;
        }
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String name = extractName(token);
        return (name.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    // public String verify(UserModel user) {

    // Authentication authentication = authenticationManager.authenticate(
    // new UsernamePasswordAuthenticationToken(user.getUsername(),
    // user.getPassword()));

    // if (authentication.isAuthenticated()) {
    // return "Success";
    // } else {
    // return "fail";
    // }
    // }
}
