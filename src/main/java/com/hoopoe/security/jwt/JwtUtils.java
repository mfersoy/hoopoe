package com.hoopoe.security.jwt;

import com.hoopoe.exception.message.ErrorMessage;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    private static final Logger logger= LoggerFactory.getLogger(JwtUtils.class);
    @Value("86400000")
    public long jwtExpirationsMs;

    @Value("hoopoe@!23")
    public  String jwtSecret;


    public String generateJwtToken(UserDetails userDetails) {
        return Jwts.builder().setSubject(userDetails.getUsername()).setIssuedAt(new Date()).
                setExpiration(new Date(new Date().getTime()+jwtExpirationsMs)).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    public String  getEmailFromToken(String token){
        return  Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public  boolean validateJwtToken(String token){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException
                | SignatureException | IllegalArgumentException e){
            logger.error(String.format(ErrorMessage.JWTTOKEN_ERROR_MESSAGE,e.getMessage()));

        }
        return  false;
    }


}
