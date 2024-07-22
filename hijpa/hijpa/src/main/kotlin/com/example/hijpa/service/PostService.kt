package com.example.hijpa.service

import com.example.hijpa.controller.CreatePostRequest
import com.example.hijpa.controller.UpdatePostRequest
import com.example.hijpa.model.BaseMember
import com.example.hijpa.model.Episode
import com.example.hijpa.model.Post
import com.example.hijpa.model.PostDTO
import com.example.hijpa.repository.EpisodeRepository
import com.example.hijpa.repository.MemberRepository
import com.example.hijpa.repository.PostRepository
import jakarta.transaction.Transactional
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import kotlin.math.E


@Service
class PostService(
    private val postRepository: PostRepository,
    private val episodeRepository: EpisodeRepository,
    private val memberRepository: MemberRepository
) {

    @Transactional
    fun findAllPost(condition:String,value:String,pageable: Pageable= PageRequest.of(0,1)):List<PostDTO>{
        return postRepository.findPostByCondition(condition,value,pageable)
    }

    fun findAllWithMember():List<Post>{
        return postRepository.finAllWithMember()
    }
    @Transactional
    fun writePost(createPostRequest: CreatePostRequest):PostDTO{

        val member= memberRepository.findById(createPostRequest.memberId).orElseThrow(){
            throw IllegalArgumentException("해당 멤버가 없습니다.")
        }

        val post= Post(title=createPostRequest.title,content=createPostRequest.content,member=member, isEpisode = createPostRequest.isEpisode)
        throw RuntimeException("트랜잭션 테스트")
        if(createPostRequest.isEpisode){
            val episode= Episode(post=post)
            episodeRepository.save(episode)
        }
        return PostDTO.toDTO(post)
    }

    fun postCount():Long{
        return postRepository.count()
    }

    fun episodeCount():Long{
        return episodeRepository.count()
    }

    fun findPostPagingWithSort(pageable:Pageable):List<PostDTO>{
        return postRepository.findPostsPagingWithSort(pageable)
    }

    fun findPostById(id: Long): PostDTO {
        val post=postRepository.findById(id).orElseThrow(){
            throw IllegalArgumentException("해당 게시물이 없습니다.")
        }
        return PostDTO.toDTO(post)
    }

    @Transactional
    fun updatePost(updatePostRequest: UpdatePostRequest): PostDTO {
        val post=postRepository.findById(updatePostRequest.id).orElseThrow(){
            throw IllegalArgumentException("해당 게시글이 없습니다.")
        }

        if (post.isEpisode != updatePostRequest.isEpisode){
            if(updatePostRequest.isEpisode){
                val episode= Episode(post=post)
                episodeRepository.save(episode)
            }else{
                episodeRepository.findByPost(post)?.let{
                    episodeRepository.delete(it)
                }
            }
        }
        post.title=updatePostRequest.title
        post.content=updatePostRequest.content
        post.isEpisode=updatePostRequest.isEpisode
        return PostDTO.toDTO(post)
    }




}

