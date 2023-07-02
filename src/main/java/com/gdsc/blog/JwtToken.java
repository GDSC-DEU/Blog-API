package com.gdsc.blog;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.Duration;
import java.util.Date;

public class JwtToken {

    /**
     * create jwt token
     * @return jwt token
     */
    public String createJwtToken(){
        Date now = new Date(); //now date

        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE) //set header
            .setIssuer("ADMIN") //set issuer
            .setIssuedAt(now) //set issued date
            .setExpiration(new Date(now.getTime() + Duration.ofMinutes(30).toMillis())) //set expire date
            .claim("username", "test") //set payload
            .signWith(SignatureAlgorithm.HS256, "secret") //set signature
            .compact(); //create token
    }
}
