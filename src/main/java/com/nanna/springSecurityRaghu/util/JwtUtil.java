package com.nanna.springSecurityRaghu.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {
    
    @Value ( "${app.secret}" )
    private String secret;
    
    
    
//    validation
    
    public boolean validateToken(String token , String username) {
    
        String tokenUsername = getSubject ( token );
        return username.equals ( tokenUsername ) && !isTokenExpire ( token );
    }
    
    
    
    
//    read subject / username
    public String getSubject(String token ) {
    
        String subject = getClaims ( token ).getSubject ();
        return subject;
    
    }
    
    public boolean isTokenExpire(String token ) {
    
        Date expirydate = getExpirydate ( token );
        boolean isExpired   = expirydate.before ( new Date ( System.currentTimeMillis () ) );
        return isExpired;
    
    }
    
    
    
    
//    reading expiry date
    public Date getExpirydate(String token ) {
    
        return getClaims ( token ).getExpiration ();
    }
    
    
    
    
    
    
    
    
//    read claims
    
    public Claims getClaims(String  token) {
    
        Claims claimBody = Jwts.parser ()
                .setSigningKey ( secret.getBytes () )
                
                .parseClaimsJws ( token )
                .getBody ();
    
    
        return claimBody;
    
    }
    
    
    
    public String generateToken( String subject ) {



        return Jwts.builder ()
                .setSubject ( subject )
                .setIssuer ( "nannaSoft" )
                .setIssuedAt ( new Date (System.currentTimeMillis ()) )
                .setExpiration ( new Date (System.currentTimeMillis ()+ TimeUnit.MINUTES.toMillis (15) ) )
                .signWith ( SignatureAlgorithm.HS512, secret.getBytes () )
                .compact ();
    }
}
