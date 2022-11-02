package com.ensibuuko.android_dev_coding_assigment.api

import com.ensibuuko.android_dev_coding_assigment.data.models.Post
import retrofit2.http.*

interface PostApi {
    @GET("posts")
    suspend fun getPosts(): List<Post>

    @POST("posts")
    suspend fun addPost(@Body post: Post): Post

    @PUT("posts/{postId}")
    suspend fun updatePost(@Path("postId") postId: String, @Body post: Post): Post

    @DELETE("posts/{postId}")
    suspend fun deletePost(@Path("postId") postId: String): Post
}