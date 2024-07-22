package com.example.hijpa.service

import com.example.hijpa.model.Member
import com.example.hijpa.repository.MemberRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class PostServiceTest{

    @Autowired
    lateinit var postService: PostService;
    @Autowired
    lateinit var memberRepository: MemberRepository
    @Test
    fun writePost(){
        val member= Member(name="andy",email="sjho714@naver.com")
        memberRepository.save(member)
        assertThrows<RuntimeException>{
            postService.writePost(member,"title","content",true)
        }
//        val postCount=postService.postCount()
//        val episodeCount=postService.episodeCount()

//        println("post count:$postCount,episodecount:$episodeCount")
    }
}