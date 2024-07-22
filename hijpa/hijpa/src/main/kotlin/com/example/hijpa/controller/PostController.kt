package com.example.hijpa.controller

import com.example.hijpa.model.Post
import com.example.hijpa.model.PostDTO
import com.example.hijpa.service.PostService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.*


data class CreatePostRequest(
    val memberId:Long,
    val title:String,
    val content:String,
    val isEpisode:Boolean

)
data class  UpdatePostRequest(
    var id:Long,
    val title: String,
    val content: String,
    val isEpisode: Boolean
)

@RestController
@RequestMapping("/posts")
class PostController (private val postService: PostService){

    @PostMapping
    fun createPost(@RequestBody createPostRequest:CreatePostRequest){
       return  postService.writePost(createPostRequest)
    }

    @GetMapping
    fun getPosts(@RequestParam condition:String ="title",@RequestParam value:String ="",@RequestParam page:Int=1,@RequestParam size:Int=10):List<PostDTO>{
        if (condition=="id"){
            return postService.findAllPost(condition,value)
        }
        val pageabe=PageRequest.of(page-1,size,Sort.by())
    }

    @GetMapping("/{id}")
    fun getPost(@PathVariable id:Long):PostDTO{
        return postService.findPostById(id);
    }

    @PutMapping("/{id}")
    fun updatePost(@PathVariable id:Long,@RequestBody updatePostRequest:UpdatePostRequest):PostDTO{
        updatePostRequest.id=id
        return postService.updatePost(updatePostRequest)
    }


}