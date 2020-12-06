package com.example.fam.security

import com.example.fam.domain.User
import io.jsonwebtoken.*
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.security.SignatureException
import java.util.*
import kotlin.collections.HashMap

@Component
class JwtTokenProvider {

    //Generate the token
    fun generateToken(authentication: Authentication): String {

        val user: User = authentication.principal as User
        val now = Date(System.currentTimeMillis())
        val expiryDate = Date(now.time + 3000_0000)

        val userId = user.Id.toString()
        val claims: MutableMap<String, Any> = HashMap()
        claims.put("id", user.Id);
        claims.put("email", user.email);
        claims.put("name", user.name);

        return Jwts.builder()
                .setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, "SecretKeyToGenJWTs")
                .compact();

    }

    //Validate the token
    fun validateToken(token: String): Boolean {
        try {
            Jwts.parser().setSigningKey("SecretKeyToGenJWTs").parseClaimsJws(token);
            return true;
        } catch (ex: SignatureException) {
            println("Invalid JWT Signature");
        } catch (ex: MalformedJwtException) {
            println("Invalid JWT Token");
        } catch (ex: ExpiredJwtException) {
            println("Expired JWT token");
        } catch (ex: UnsupportedJwtException) {
            println("Unsupported JWT Signature");
        } catch (ex: IllegalArgumentException) {
            println("JWT claims strung is empty");
        }
        return false;

    }

    //Get user Id from token
    fun getUserIdFromJWT(token: String): Int {
        val claims: Claims = Jwts.parser().setSigningKey("SecretKeyToGenJWTs").parseClaimsJws(token).body;
        val id = claims["id"]
        return id as Int
    }

}