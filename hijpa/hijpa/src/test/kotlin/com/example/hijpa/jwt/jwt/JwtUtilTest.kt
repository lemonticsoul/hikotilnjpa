package com.example.hijpa.jwt.jwt

import io.jsonwebtoken.security.Keys
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class JwtUtilTest {


    @Autowired
    lateinit var jwtUtil: JwtUtil

    @Test
    fun generateToken() {
        val key= Keys.hmacShaKeyFor(jwtUtil.secretKey.toByteArray())
        val token=jwtUtil.generateToken("test", hashMapOf())
        println(token)

        val payload=jwtUtil.getClaims(token).payload


    }

    @Test
    fun getUsername() {
        val token=jwtUtil.getUsername(testToken)

    }

    @Test
    fun isTokenExpired() {
    }

    @Test
    fun validateToken() {

        val result=jwtUtil.isTokenExpired(testToken)

    }
}