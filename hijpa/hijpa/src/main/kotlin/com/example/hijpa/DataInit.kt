package com.example.hijpa

import com.example.hijpa.model.Member
import com.example.hijpa.model.Post
import com.example.hijpa.repository.MemberRepository
import com.example.hijpa.repository.PostRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class DataInit(private val memberRepository: MemberRepository,
    private val postRepository: PostRepository,
    private val passwordEncoder: PasswordEncoder
):CommandLineRunner {
    override fun run(vararg args:String?) {
        //Post 20개 추가

        val posts= mutableListOf<Post>()

        for (i in 1 .. 20){
            val member= Member(name="member$i",email="member$i@email.com",password=passwordEncoder.encode("1234$i"))
            memberRepository.save(member)
            val post=Post(title="title$i",content="content$i",member=member, isEpisode =true)
            posts.add(post)
        }
        postRepository.saveAll(posts)
    }
}