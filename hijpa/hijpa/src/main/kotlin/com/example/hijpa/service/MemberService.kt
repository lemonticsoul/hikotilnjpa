package com.example.hijpa.service

import com.example.hijpa.controller.UpdateMemberRequest
import com.example.hijpa.model.MemberDTO
import com.example.hijpa.repository.EpisodeRepository
import com.example.hijpa.repository.MemberRepository
import com.example.hijpa.repository.PostRepository
import jakarta.transaction.Transactional
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.MemberDeserializer

class MemberService(
    private val memberRepository: MemberRepository)  {

    fun getMember(id:Long): MemberDTO {
        val member=memberRepository.findById(id).orElseThrow{
            throw IllegalArgumentException("해당 멤버가 없습니다.")
        }

        return MemberDTO.toDto(member)
    }

    @Transactional
    fun updateMember(updateMemberRequest: UpdateMemberRequest): MemberDTO {
        val member=memberRepository.findById(updateMemberRequest.id).orElseThrow(){
            throw IllegalArgumentException("해당 멤버가 없습니다.")
        }
        member.email=updateMemberRequest.email
        return MemberDTO.toDto(member)
    }

    fun deleteMember(id: Long) {
        memberRepository.deleteById(id)

    }
}