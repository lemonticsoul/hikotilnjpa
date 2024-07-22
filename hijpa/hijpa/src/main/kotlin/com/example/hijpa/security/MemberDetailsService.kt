package com.example.hijpa.security

import com.example.hijpa.repository.MemberRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service


@Service
class MemberDetailsService(
    private val memberRepository: MemberRepository
) :UserDetailsService {
    override fun loadUserByUsername(email: String?): UserDetails {
        if(email.isNullOrEmpty()){
            throw IllegalArgumentException("이메일이 널값3")
        }
        val member=memberRepository.findByEmail(email!!)

        println("member:$member,role:$member.role")
        return MemberDetails(member)
    }
}