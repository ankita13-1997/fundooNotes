package com.microusers.userserver.utils;



import com.microusers.userserver.model.UserDetailsModel;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class Token {


        public String generateLoginToken(UserDetailsModel userDetails) {

            long currentTime = System.currentTimeMillis();

            return Jwts.builder()
                    .setId(String.valueOf(userDetails.userId))
                    .setSubject(userDetails.fullName)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(currentTime + 100000000))
                    .signWith(SignatureAlgorithm.HS256, "sd5745FAHFW")
                    .compact();
        }


        public String generateVerificationToken(UserDetailsModel userDetails) {

            long currentTime = System.currentTimeMillis();
            System.out.println("generate token id:   " + userDetails.userId);
            return Jwts.builder()
                    .setId(String.valueOf(userDetails.userId))
                    .setSubject(userDetails.fullName)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(currentTime +100000000))
                    .signWith(SignatureAlgorithm.HS256, "sd5745FAHFW")
                    .compact();
        }

        public UUID decodeJWT(String jwt) throws JwtException {
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey("sd5745FAHFW").parseClaimsJws(jwt).getBody();

                System.out.println("jwt id: " + claims.getId());
                return UUID.fromString(claims.getId());
            } catch (ExpiredJwtException e) {
                throw new JwtException("session time out");
            }
        }


}

