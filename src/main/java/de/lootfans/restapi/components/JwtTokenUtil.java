package de.lootfans.restapi.components;


import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class JwtTokenUtil {

    public Map<String, Claim> decodeJWT(String token) {
        DecodedJWT jwt;

        try {
            jwt = JWT.decode(token);
            return jwt.getClaims();
        } catch (JWTDecodeException exception) {
            //Invalid token
        }
        return null;
    }
}