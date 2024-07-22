package com.example.hijpa.controller

import com.example.hijpa.jwt.jwt.JwtUtil
import com.example.hijpa.security.MemberDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


data class AuthRequest(
    val email:String,
    val password:String
)

@RestController

class AuthController (
    private val jwtUtil: JwtUtil,
    private val passwordEncoder: PasswordEncoder,
    private val memberDetailsService: MemberDetailsService
){

    @PostMapping("/authenticate")
    fun authenticate(@RequestBody authRequest:AuthRequest){
        var userDetails=memberDetailsService.loadUserByUsername(authRequest.email)

        if (passwordEncoder.matches(authRequest.password,userDetails.password)){
            return jwtUtil.generateToken(authRequest.email, hashMapOf("email" to authRequest.email,
                "role" to userDetails.authorities.first().authority
            ))
        }

        return "Authentication Failed"
    }

}