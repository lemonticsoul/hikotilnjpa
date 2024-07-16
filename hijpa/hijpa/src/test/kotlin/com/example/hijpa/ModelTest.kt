package com.example.hijpa

import com.example.hijpa.model.Author
import com.example.hijpa.model.BaseMember
import com.example.hijpa.model.Member
import com.example.hijpa.repository.MemberRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles


@ActiveProfiles("test")
@DataJpaTest(showSql = true)
class ModelTest {

    @Autowired
    private lateinit var memberRepository: MemberRepository


    @Test
    fun getAllUsers(){
        val member= Member(name="andy", email="sjho714@naver.com")
        val author= Author(name= "andy2",email="sjho715@naver.com")
        val member3= Member(name= "andy3",email="sjho716@naver.com")
        val member4= Author(name= "andy4",email="sjho717@naver.com")


        memberRepository.save(member)
        memberRepository.save(author)
        memberRepository

        val basemembers:List<BaseMember> = memberRepository.findAll()
        for (user in basemembers){

        }
    }

//    @Test
//    fun `이름이 20자 넘어감` (){
//        val member=Member(name="1231o24uo1241248012840182408",email="test@mail.com")
//        assertThrows<DataIntegrityViolationException> {
//            userRepository.save(member)
//        }
//
//    }
//
//    @Test
//    fun `이메일이 유니크 한지 테스트` (){
//        val user
//        val user2=
//    }


}