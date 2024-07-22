package com.example.hijpa.controller

import com.example.hijpa.Validate.MemberValidator
import com.example.hijpa.model.BaseMember
import com.example.hijpa.model.Member
import com.example.hijpa.model.MemberDTO
import com.example.hijpa.repository.MemberRepository
import com.example.hijpa.service.MemberService
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.validation.BindException
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*


interface OnCreateError

data class CreateMemberRequests (
    @get:Size(min=1,max=20, message ="이름은 1자이상 20자 이하",groups=[OnCreateError::class])
    val name:String,
    @get:Email(message="이메일")
    val email:String
){
    fun toMemberDTO(): MemberDTO {
        return MemberDTO(name=name,email=email)
    }

}

data class UpdateMemberRequest(
    val id:Long,
    val email:String
)
@RequestMapping("/members")
@RestController
class MemberController(private val memberRepository: MemberRepository,
    private val memberService: MemberService,
    private val memberValidator:MemberValidator
) {


    @GetMapping("/createMember")
    fun createMember( @Valid
        createMemberRequests: CreateMemberRequests,bindResult: BindingResult
    ): BaseMember {

        if (bindResult.hasErrors()){
            val errorMessage =bindResult.allErrors.joinToString {it.defaultMessage ?:"검증에러"}
            throw error(errorMessage);
        }
        memberRepository.createMember(createMemberRequests.toMemberDTO())
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

    @PutMapping("/{id}")
    fun updateMember(@PathVariable id:Long,@RequestParam email:String ):MemberDTO{
        val updateMemberRequest=UpdateMemberRequest(id,email)
        return memberService.updateMember(updateMemberRequest)
    }

    @DeleteMapping("/{id}")
    fun deleteMember(@PathVariable id:Long){
        memberService.deleteMember(id)
    }





}