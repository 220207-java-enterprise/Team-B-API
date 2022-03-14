package com.revature.foundation.services;

import com.revature.foundation.dtos.responses.Principal;
import com.revature.foundation.util.auth.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {
    private final JwtConfig jwtConfig;

    public TokenService(JwtConfig jwtConfig){
        this.jwtConfig = jwtConfig;
    }

    public String generateToken(Principal principal){
        long now = System.currentTimeMillis();
        JwtBuilder jwtBuilder = Jwts.builder()
                                    .setId(principal.getId())
                                    .setIssuer("tech-project")
                                    .setIssuedAt(new Date(now))
                                    .setExpiration(new Date(now + jwtConfig.getExpiration()))
                                    .setSubject(principal.getUsername())
                                    .claim("role", principal.getRole())
                                    .signWith(jwtConfig.getSigAlg(), jwtConfig.getSigningKey());

        return jwtBuilder.compact();
    }

    public Principal extractRequesterDetails(String jwt) {

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getSigningKey())
                    .parseClaimsJws(jwt)
                    .getBody();

            Principal principal = new Principal();
            principal.setId(claims.getId());
            principal.setUsername(claims.getSubject());
            principal.setRole(claims.get("role", String.class));

            return principal;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
