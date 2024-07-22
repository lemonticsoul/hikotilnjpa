package com.example.hijpa.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.server.savedrequest.NoOpServerRequestCache


@Configuration
class SecurityConfig {


    @Bean
    fun userDetailsService(): UserDetailsService {
        val userDetails=List(20){i->
            User.withUsername("member$i")
                .password("1234")
                .authorities("read")
                .build()
        }

        return InMemoryUserDetailsManager(userDetails)
    }

    @Bean
    fun passwordEncoder():PasswordEncoder{
        return BCryptPasswordEncoder()
    }

    @Bean
    fun securityFilterchain(http:HttpSecurity):SecurityFilterChain{
        http
            .csrf{it.disable()}
            .httpBasic{}
            .addFilterBefore(UsernamePasswordAuthenticationFilter)
            .formLogin{it.defaultSuccessUrl("home",true)}
            .authorizeHttpRequests{
                authorize->authorize.
                requestMatchers("/hello").hasRole("member")
                .requestMatchers("/home").hasRole("Author")
                .anyRequest().denyAll()
            }
        return http.build()
    }

    @Bean
    fun userSecretKeys():Map<String,String>{
        val secretKeys= mutableMapOf()
    }

}