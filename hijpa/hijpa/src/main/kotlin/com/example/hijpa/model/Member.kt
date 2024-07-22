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
    var email:String,
    var role:String ="ROLE_MEMBER",
    var password:String="1234",
    val createDt: Instant =Instant.now(),
    val updateDt: Instant = Instant.now()

){
    override fun toString(): String {
        println(this::class.simpleName)
        return "User($id,$name,$email,$createDt,$role)"
    }

//    fun fromDto(dto:MemberDTO):BaseMember{
//        return when(this){
//            is Member->Member(dto.name,dto.email)
//            is Author->Author(dto.name,dto.email)
//            else->throw NotImplementedError("멤버 클래스가 아닙니다.")
//        }
//    }
}


@Entity
@DiscriminatorValue("NORMAL")

class Member(
    name:String,
    email:String,
    password:String
): BaseMember(name= name,email = email,password=password,role='ROLE_MEMBER')


@Entity
@DiscriminatorValue("AUTHOR")
class Author(
    name:String,
    email:String,
    password:String,
): BaseMember(name= name,email = email)


@Entity
class Post(

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    val id:Long =0,
    var title:String,
    var content:String,
    val isEpisode:Boolean=false,

    @ManyToOne
    @JoinColumn(name="member_id")
    val member:BaseMember,
    val createDt:Instant=Instant.now(),
    val updateDt: Instant=Instant.now(),

)


@Entity
class Episode(
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    val id:Long =0,

    @OneToOne
    @JoinColumn(name="post_id")
    val post:Post,

    @ManyToOne
    @JoinColumn(name="book_id")
    val book:Book?=null

)

@Entity
class Book(
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    val id:Long =0,
    val title:String,

    @ManyToOne
    @JoinColumn(name="member_id")
    val member:BaseMember,
    val publisher:String="앤디출판",
    val createDt:Instant=Instant.now(),
    val updateDt: Instant=Instant.now(),

)

@Entity

class BookShelf(
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    val id:Long =0,
    @ManyToOne
    @JoinColumn(name="member_id")
    val member:BaseMember,
    @ManyToOne
    @JoinColumn(name="book_id")
    val book:Book,
    val createDt:Instant=Instant.now(),
    val updateDt: Instant=Instant.now(),
)