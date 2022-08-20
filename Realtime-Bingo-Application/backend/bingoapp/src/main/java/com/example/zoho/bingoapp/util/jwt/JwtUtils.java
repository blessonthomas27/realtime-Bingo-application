package com.example.zoho.bingoapp.util.jwt;

import com.example.zoho.bingoapp.model.Room;
import com.example.zoho.bingoapp.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtils {

    private static String secret="this_is_secretthis_is_secretthis_is_secretthis_is_secretthis_is_secretthis_is_secret";
    private static long expDur= 60*60*60;// makit 3
    public String generateJwt(String roomId,String userId){

        long milliTime=System.currentTimeMillis();
        long expTime=expDur+milliTime*1000;
        Date issuedAt= new Date(milliTime);
        Date expirayAt= new Date(expTime);

        //claims
        Claims claims = Jwts.claims().setIssuer(roomId).setIssuer(userId).setIssuedAt(issuedAt).setExpiration(expirayAt);

        //generate jwt using claims
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder().setClaims(claims).signWith(key,SignatureAlgorithm.HS512).compact();
    }

    public void verify(String authorization) throws Exception{

        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authorization);
        }catch (Exception e){
                throw new Exception();
        }
        System.out.println("true");
    }
}
