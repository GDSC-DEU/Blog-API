package com.gdsc.blog;

import com.gdsc.blog.user.Member;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.Operation;
import java.time.Duration;
import java.util.Date;

public class JwtToken{

    /**
     * create jwt token
     * @Param member object
     * @return jwt token
     */
    @Operation(summary = "create jwt token")
    //매개변수로 member를 가져올지, member의 username, password를 가져올지 생각해 봐야한다
    public String createJwtToken(Member member){
        String username = member.getUsername(); //get username
        String password = member.getPassword(); //get password
        Date now = new Date(); //now date


        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE) //set header
            .setIssuer("ADMIN") //set issuer
            .setIssuedAt(now) //set issued date
            .setExpiration(new Date(now.getTime() + Duration.ofMinutes(30).toMillis())) //set expire date
            .claim("username", username) //set payload
            .claim("password", password)
            .signWith(SignatureAlgorithm.HS256, "secret") //set signature
            .compact(); //create token
    }
}
