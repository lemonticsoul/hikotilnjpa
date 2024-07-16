package com.example.hijpa.model

import jakarta.persistence.*
import java.time.Instant


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type", discriminatorType = DiscriminatorType.STRING)
abstract class BaseMember (

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    val id:Long =0,
    @Column(length=20)
    val name:String,
    @Column(unique=true)
    val email:String,
    val createDt: Instant =Instant.now(),
    val updateDt: Instant = Instant.now()

){
    override fun toString(): String {
        println(this::class.simpleName)
        return "User($id,$name,$email,$createDt)"
    }

    fun fromDto(dto:MemberDTO)
}


@Entity
@DiscriminatorValue("NORMAL")

class Member(
    name:String,
    email:String,

): BaseMember(name= name,email = email)


@Entity
@DiscriminatorValue("AUTHOR")
class Author(
    name:String,
    email:String,

): BaseMember(name= name,email = email)
