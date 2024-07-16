package com.example.hijpa.controller

import com.example.hijpa.model.BaseMember
import com.example.hijpa.model.Member
import com.example.hijpa.repository.MemberRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/member")
@RestController
class MemberController(private val memberRepository: MemberRepository) {


    @GetMapping("/createMember")
    fun createMember(
        @RequestParam("name") name:String,
        @RequestParam("email") email:String
    ): BaseMember {
        val member= Member(name,email)
        memberRepository.save(member)
        return member
    }

    @GetMapping("allMemnbers")
    fun allMembers():List<BaseMember>{
        return memberRepository.findAll()
    }

//    @GetMapping("/init")
//    fun initMember(){
//        val members= mutableListOf<Member>()
//        for (i in 1 ..100){
//            val name=
//        }
//        memberRepository.saveAll(members)
//    }
    @GetMapping("/findByEmailContaining")
    fun findByEmailContaining(@RequestParam("email") email:String):List<BaseMember>{
        return memberRepository.findByEmailContaining(email)
    }

    @GetMapping("/countDistinctByName")
    fun countDistinctByName(@RequestParam("name")name:String):Int{
        return memberRepository.countDistinctByName(name);
    }

    @GetMapping("/finAllMemberpaging")
    fun findAllMembersWithPaging(@RequestParam("page")page:Int,@RequestParam("size")size:Int): Page<BaseMember>{
        val pageable=PageRequest.of(page,size, Sort.by("id"))
        return memberRepository.finAllMembersWithPagin(pageable);
    }





}