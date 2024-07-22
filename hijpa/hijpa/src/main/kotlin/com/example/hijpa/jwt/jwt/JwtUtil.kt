package com.example.hijpa.jwt.jwt

import com.example.hijpa.security.MemberDetailsService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*
import javax.crypto.SecretKey
import kotlin.collections.HashMap


@Component
class JwtUtil (
    private val memberDetailsService: MemberDetailsService

){
    //hs256 /8 =32 2글자 이상
    val secretKey="abcdefghijk1235asdfasdfasdfasdfasdf"

    fun generateToken(username:String,claims:HashMap<String,Any>):String{
        val key= Keys.hmacShaKeyFor(secretKey.toByteArray())
        val now= Instant.now()

        return Jwts.builder()
            .claims(claims)
            .subject(username)
            .issuedAt(Date(now.toEpochMilli())
            )
            .expiration(Date(now.plusSeconds(3600).toEpochMilli()))
            .signWith(key,Jwts.SIG.HS256).compact()
    }

    fun getClaims(token:String): Jws<Claims> {
        val key=getSecretKey()
        try{
            return Jwts.parser().verifyWith(key).build().parseSignedClaims(token)
        }catch(e:ExpiredJwtException){
            throw ExpiredJwtException(null,null,"토큰이 만료되었습니다")
        }
    }

    fun getUsername(token:String):String{
        return getClaims(token).payload.subject
    }

    private fun getSecretKey(): SecretKey {
        return Keys.hmacShaKeyFor(secretKey.toByteArray())
    }
    fun isTokenExpired(token:String):Boolean{
        val key=getSecretKey()
        val claims=getClaims(token)
        return claims.payload.expiration.before(Date())
    }

    fun validateToken(token:String,username:String):Boolean{
        return try{
            !isTokenExpired(token) && getClaims(token).payload.subject ==username


        }catch(e:Exception){
            return false;
        }
    }

    fun getAuthentication(token:String):Authentication{
        val claims=getClaims(token)
        val userDetails=memberDetailsService.loadUserByUsername(claims.payload.subject)
        return UsernamePasswordAuthenticationToken(userDetails,null,userDetails.authorities)
    }
}