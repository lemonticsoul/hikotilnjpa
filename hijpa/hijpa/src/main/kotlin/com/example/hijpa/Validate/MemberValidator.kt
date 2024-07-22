package com.example.hijpa.Validate

import com.example.hijpa.controller.CreateMemberRequests
import com.example.hijpa.controller.CreatePostRequest
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.Validator


@Component
class MemberValidator:Validator {
    override fun supports(clazz: Class<*>): Boolean {
        return CreateMemberRequests::class.java ==clazz
    }

    override fun validate(target: Any, errors: Errors) {
        val createMemberRequests=target as CreateMemberRequests
        if (createMemberRequests.name.length>20){
            errors.rejectValue("name","MEMBER_NAME_LENGTH_ERROR","이름의 크기는 20자 이상이여야 한다.")
        }
        val emailRegex =Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9._]+\\\\.[a-zA-Z]{2,6}\\\$")

        if(createMemberRequests.email =="" ||!emailRegex.matches(createMemberRequests.email)){
            errors.rejectValue("email","EMAIL_FORMAT_ERROR","이메일정보가 올바르지 못함")
        }
    }
}