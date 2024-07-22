package com.example.hijpa.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.Instant

class MemberDto {
}


class PostDTO(
    val id:Long =0,
    val title:String,
    val content:String,
    val isEpisode:Boolean=false,
    val memberName:String ?=null,
    val createDt: Instant = Instant.now(),
    val updateDt: Instant = Instant.now(),
    ){
    constructor(
       id:Long,
        title:String,
        content:String,
        isEpisode: Boolean,
        member:BaseMember,
        createDt: Instant,
        updateDt: Instant



    ):this(id,title,content,isEpisode,memberName=member.name,createDt,updateDt)

    companion object{
        fun toDTO(entity:Post):PostDTO{
           return PostDTO( entity.id,entity.title,entity.content,entity.isEpisode,entity.member.name,entity.createDt,entity.updateDt)
        }
    }
}

class MemberDTO(
    val id:Long?=0,
    val name:String,
    val email:String,
    val type:String="NORMAL",
    @JsonIgnore
    val password:String,


){
    companion object{
        fun toDto(entity:BaseMember):MemberDTO{
            val type=when(entity){
                is Member -> "NORMAL"
                is Author ->"AUTHOR"
                else-> "UNKNOWN"
            }
            return MemberDTO(entity.id,entity.name,entity.email,type, password = entity.password)
        }
    }
}
